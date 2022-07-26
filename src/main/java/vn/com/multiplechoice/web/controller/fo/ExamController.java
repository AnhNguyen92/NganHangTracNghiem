package vn.com.multiplechoice.web.controller.fo;

import java.util.ArrayList;
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

import vn.com.multiplechoice.business.converter.TestConverter;
import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.web.dto.ExamDto;
import vn.com.multiplechoice.web.model.MCQDto;

@Controller
@RequestMapping(value = "/fo/do-exam")
public class ExamController {
	private static final Logger log = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	TestService testService;

	@Autowired
	TestConverter testConverter;

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
		markExam(examDto);

		return "redirect:/fo/index";
	}

	private void markExam(ExamDto examDto) {
		double totalScore = 0.0;
		int totalRightAnswer = 0;
		Test test = testService.findById(examDto.getId());
		Set<Question> questions = test.getQuestions();
		double pointPerQuestion = 10.0 / questions.size();
		for (MCQDto mcqDto : examDto.getQuestions()) {
			Question question = questions.stream().filter(q -> q.getId().equals(mcqDto.getId())).findFirst().get();
			List<String> rightAnswerLst = getRightAnswerLst(question);
			List<String> selectedAnswerLst = mcqDto.getSelectedAnswers();
			if (rightAnswerLst.equals(selectedAnswerLst)) {
				log.info("found true question");
				totalRightAnswer++;
				if (question.getScore() == null) {
					totalScore += pointPerQuestion;
				} else {
					rightAnswerLst.retainAll(mcqDto.getSelectedAnswers());
					totalScore += pointPerQuestion * rightAnswerLst.size();
				}
//				totalScore +=  question.getScore();
			}
		}
		System.out.println(totalRightAnswer);
		System.out.println(totalScore);
		// save to database here
	}

	private List<String> getRightAnswerLst(Question question) {
		List<String> rightAnswerLst = new ArrayList<>();
		String[] labels = question.getRightAnswer().split(",");
		for (String label : labels) {
			switch (label) {
			case "A":
				rightAnswerLst.add(question.getAnswerA());
				break;
			case "B":
				rightAnswerLst.add(question.getAnswerB());
				break;
			case "C":
				rightAnswerLst.add(question.getAnswerC());
				break;
			case "D":
				rightAnswerLst.add(question.getAnswerD());
				break;
			case "E":
				rightAnswerLst.add(question.getAnswerE());
				break;
			case "F":
				rightAnswerLst.add(question.getAnswerF());
				break;
			case "G":
				rightAnswerLst.add(question.getAnswerG());
				break;
			default:
				rightAnswerLst.add(question.getAnswerH());
				break;
			}

		}
		return rightAnswerLst;
	}

}
