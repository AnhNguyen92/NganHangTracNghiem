package vn.com.multiplechoice.web.controller.fo;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.com.multiplechoice.business.converter.QuestionConverter;
import vn.com.multiplechoice.business.converter.TestConverter;
import vn.com.multiplechoice.business.service.ExamResultService;
import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.model.ExamResult;
import vn.com.multiplechoice.dao.model.ExamResultItem;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.dto.ExamDto;
import vn.com.multiplechoice.web.dto.ExamResultDTO;
import vn.com.multiplechoice.web.dto.ExamResultItemDTO;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

@Controller
@RequestMapping(value = "/fo/do-exam")
public class ExamController {
	private static final Logger log = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	TestService testService;

	@Autowired
	TestConverter testConverter;

	@Autowired
	private QuestionConverter questionConverter;
	
	@Autowired
	private ExamResultService examResultService;
	
	@Autowired
	OnlineUserUtil onlineUserUtil;
	
	@GetMapping("/{id}")
	public String exam(Model model, @PathVariable("id") Long id) {
		log.info("===  Start do exam with test has id = {}  ===", id);
		Test test = testService.findById(id);
		String executeTime = test.getExecuteTime();

		model.addAttribute("executeTime", executeTime);
		ExamDto examDto = testConverter.toExam(test);
		model.addAttribute("examDto", examDto);
		return "/fo/do-exam";
	}

	@PostMapping("")
	public String doExam(Model model, @ModelAttribute ExamDto examDto) {
		log.info("{}", examDto);
		double totalScore = 0.0;
		Test test = testService.findById(examDto.getId());
		Set<Question> questions = test.getQuestions();
		double pointPerQuestion = 10.0 / questions.size();
		ExamResultDTO examResultDTO = new ExamResultDTO();
		ExamResult examResult = new ExamResult();
		examResult.setExecuteTime(new Date());
		examResult.setTest(test);
		examResult.setUser(onlineUserUtil.getOnlineUser());
		for (MCQDto mcqDto : examDto.getQuestions()) {
			Question question = questions.stream().filter(q -> q.getId().equals(mcqDto.getId())).findFirst().get();
			ExamResultItem examResultItem = new ExamResultItem();
			examResultItem.setQuestion(question);
			List<String> rightAnswerLst = getRightAnswerLst(question);
			List<String> selectedAnswerLst = mcqDto.getSelectedAnswers();
			List<String> selectedAnswerValues = mapLabelToAnswerValue(question, selectedAnswerLst);
			List<String> rightAnswerValues = mapLabelToAnswerValue(question, rightAnswerLst);
			QuestionType type = question.getType();
			ExamResultItemDTO examResultItemDTO = new ExamResultItemDTO();
			examResultItemDTO.setType(questionConverter.vietNameseQuestionType(type));
			examResultItemDTO.setQuestionContent(question.getContent());
			examResultItemDTO.setRightAnswer(rightAnswerValues);
			examResultItemDTO.setSelectAnswer(selectedAnswerValues);
			examResultItemDTO.setExplain(question.getSuggest());
			examResultItem.setSelectedAnswer(String.join(",", selectedAnswerLst));
			if (type != QuestionType.MATCHING) {
				Collections.sort(selectedAnswerLst);
			}
			if (rightAnswerLst.equals(selectedAnswerLst)) { // choose all right answer
				log.info("found true question");
				examResultItem.setCount(1);
				examResultItem.setTotalScore(100);
				
				examResultItemDTO.setCount(1);
				examResultDTO.increaseTotalTrueAnswer();
				examResultItemDTO.setScore(100);
				totalScore += pointPerQuestion;
			} else { // some selected answers are true
				int sum = 0;
				String[] scores = question.getScore().split(",");
				for (int i = 0; i < scores.length; i++) {
					sum += Integer.parseInt(scores[i]);
				}
				// check all score is zero first
				if (sum == 0) { // all answer must be true to get all question score
					examResultItemDTO.setScore(0);
					examResultItem.setTotalScore(0);
				} else { // each true answer has specific score
					for (int i = 0; i < rightAnswerLst.size(); i++) {
						if (rightAnswerLst.get(i).equals(selectedAnswerLst.get(i))) {
							examResultItemDTO.setScore(examResultItemDTO.getScore() + Integer.parseInt(scores[i]));
						}
					}
					examResultItem.setTotalScore(examResultItemDTO.getScore());
				}
				totalScore += pointPerQuestion * examResultItemDTO.getScore() / 100;
				examResultItem.setTotalScore(totalScore);
				
			}
			examResultDTO.getExamResultItemDTOs().add(examResultItemDTO);
			examResult.addExamReultItems(examResultItem);
			
		}
		examResultDTO.setTotalScore(totalScore);
		model.addAttribute("examResultDTO", examResultDTO);
		// save to database here
		examResultService.save(examResult);
		
		return "/fo/exam-result";
	}

	@GetMapping("/{id}/export")
	private void export(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) {
		Test test = testService.findById(id);
		XWPFDocument document = null;
		try {
			if (test.getHeader() != null) {
				FileInputStream inputStream = new FileInputStream(new File(test.getHeader().getSourcePath()));
				document = new XWPFDocument(inputStream);
			} else {
				document = new XWPFDocument();
			}
			XWPFParagraph paragraph = document.createParagraph();
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun run = paragraph.createRun();
			run.setText(test.getContent());
			run.setBold(true);
			run.addBreak();
			FileOutputStream out = new FileOutputStream(new File("dethi" + Calendar.getInstance().getTimeInMillis() + ".docx"));
			document.write(out);
			out.close();
		} catch (Exception e) {
			log.error("{}", e.getMessage());
		}
	}

	private List<String> mapLabelToAnswerValue(Question question, List<String> labels) {
		List<String> answerValues = new ArrayList<>();
		for (String label : labels) {
			switch (label) {
			case "A":
				answerValues.add(question.getAnswerA());
				break;
			case "B":
				answerValues.add(question.getAnswerB());
				break;
			case "C":
				answerValues.add(question.getAnswerC());
				break;
			case "D":
				answerValues.add(question.getAnswerD());
				break;
			case "E":
				answerValues.add(question.getAnswerE());
				break;
			case "F":
				answerValues.add(question.getAnswerF());
				break;
			case "G":
				answerValues.add(question.getAnswerG());
				break;
			default:
				answerValues.add(question.getAnswerH());
				break;
			}
		}

		return answerValues;
	}

	private List<String> getRightAnswerLst(Question question) {
		List<String> rightAnswerLst = new ArrayList<>();
		String[] labels = question.getRightAnswer().split(",");
		Collections.addAll(rightAnswerLst, labels);
		return rightAnswerLst;
	}

}
