package vn.com.multiplechoice.web.controller.fo;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.QuestionConverter;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

@Controller
@RequestMapping(value = "/fo/questions")
public class QuestionController {
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	
	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionConverter questionConverter;
    
	@Autowired
    private OnlineUserUtil onlineUserUtil;
    
	@GetMapping("/edit/{id}")
	public String detail(Model model, @PathVariable Long id) {
		log.info("===== EDIT one answer question form for id = {} =====", id);
		User user = onlineUserUtil.getOnlineUser();
		Question question = questionService.findById(id);
		if (question == null || !question.getUser().equals(user)) {
			return "/fo/404";
		}
		MCQDto mcqDto = questionConverter.toDto(question);
		model.addAttribute("mcqDto", mcqDto);
		log.info("===== EDIT one answer question form for id = {} end =====", id);
		
		return targetPageByType(question.getType());
	}
	
	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable Long id) {
		log.info("START delete question with id = {}", id);
		User user = onlineUserUtil.getOnlineUser();
		Question question = questionService.findById(id);
		if (question == null || !question.getUser().equals(user)) {
			return "/fo/404";
		}
		Set<Test> tests = question.getTests();
		for (Test test : tests) {
			test.removeQuestion(question);
		}
		questionService.deleteQuestion(question.getId());
		
		log.info("END of delete question by id = {}", id);
		
		
		return "redirect:/fo/user/questions";
	}
	
	private String targetPageByType(QuestionType type) {
		switch (type) {
		case ONE_ANSWER:
			return "fo/create-one-ans-question";
		case MULTIPLE_ANSWER:
			return "fo/create-multiple-ans-question";
		case MATCHING:
			return "fo/create-matching-question";
		case FILLING:
			return "fo/create-filling-question";
		case GROUP_FILLING:
			return "fo/create-group-filling-question";
		case UNDERLINE:
			return "fo/create-underline-question";
		case YES_NO:
			return "fo/create-yes-no-question";
		default: // TRUE_FALSE
			return "fo/create-true-false-question";
		}
		
	}
}
