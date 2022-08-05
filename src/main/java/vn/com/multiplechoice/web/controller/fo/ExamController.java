package vn.com.multiplechoice.web.controller.fo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.QuestionConverter;
import vn.com.multiplechoice.business.converter.TestConverter;
import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.dto.ExamDto;
import vn.com.multiplechoice.web.dto.ExamResultDTO;
import vn.com.multiplechoice.web.dto.ExamResultItemDTO;
import vn.com.multiplechoice.web.model.MCQDto;

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
		int totalRightAnswer = 0;
		Test test = testService.findById(examDto.getId());
		Set<Question> questions = test.getQuestions();
		double pointPerQuestion = 10.0 / questions.size();
		ExamResultDTO examResultDTO = new ExamResultDTO();
		for (MCQDto mcqDto : examDto.getQuestions()) {
			Question question = questions.stream().filter(q -> q.getId().equals(mcqDto.getId())).findFirst().get();
			List<String> rightAnswerLst = getRightAnswerLst(question);
			List<String> selectedAnswerLst = mcqDto.getSelectedAnswers();
			List<String> selectedAnswerValues = mapLabelToAnswerValue(question, selectedAnswerLst);
			List<String> rightAnswerValues = mapLabelToAnswerValue(question, rightAnswerLst);
			ExamResultItemDTO examResultItemDTO = new ExamResultItemDTO();
			examResultItemDTO.setType(questionConverter.vietNameseQuestionType(question.getType()));
			examResultItemDTO.setQuestionContent(question.getContent());
			examResultItemDTO.setRightAnswer(rightAnswerValues);
			examResultItemDTO.setSelectAnswer(selectedAnswerValues);
			examResultItemDTO.setExplain(question.getSuggest());

			if (mcqDto.getType() != QuestionType.MATCHING) {
				Collections.sort(selectedAnswerLst);
			}
			if (rightAnswerLst.equals(selectedAnswerLst)) {
				log.info("found true question");
				examResultItemDTO.setCount(1);
				examResultDTO.increaseTotalTrueAnswer();
				totalRightAnswer++;
				if (question.getScore() == null) {
					examResultItemDTO.setScore(100);
					totalScore += pointPerQuestion;
				} else {
					String[] scores = question.getScore().split(",");
					// check all score is zero first
					for (int i = 0; i < rightAnswerLst.size(); i++) {
						if (rightAnswerLst.get(i).equals(selectedAnswerLst.get(i))) {
							examResultItemDTO.setScore(examResultItemDTO.getScore() + Integer.parseInt(scores[i]));
						}
					}

					rightAnswerLst.retainAll(mcqDto.getSelectedAnswers());
					totalScore += pointPerQuestion * rightAnswerLst.size();
				}
//				totalScore +=  question.getScore();
			}
			examResultDTO.getExamResultItemDTOs().add(examResultItemDTO);
		}
		model.addAttribute("examResultDTO", examResultDTO);
		System.out.println(totalRightAnswer);
		System.out.println(totalScore);
		// save to database here

		return "/fo/exam-result";
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
