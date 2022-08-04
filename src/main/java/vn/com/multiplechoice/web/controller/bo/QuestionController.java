package vn.com.multiplechoice.web.controller.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.multiplechoice.business.converter.QuestionConverter;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.criteria.QuestionCriteria;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.dao.model.paging.Paged;
import vn.com.multiplechoice.dao.model.paging.Paging;
import vn.com.multiplechoice.web.model.MCQDto;

@Controller
@RequestMapping("/bo/questions")
public class QuestionController {
	private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

	@Autowired
	private QuestionService questionService;

	@Autowired
	private QuestionConverter questionConverter;
	
	@GetMapping("")
	public String list(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int size,
			QuestionCriteria questionCriteria, Model model) {
		logger.info("start get list question");
		logger.info("{}", questionCriteria);
		logger.info("pageNumber = {}", pageNumber);
		logger.info("pageSize = {}", size);

		Paged<Question> paged = new Paged<>();
		List<Question> questions = questionService.findAll(questionCriteria);
		if (questionCriteria.getSize() != null) {
			size = questionCriteria.getSize().getValue();
		}
		int pageSize = pageNumber * size;
		int start = questions.isEmpty() ? 0 : ((pageNumber - 1) * size + 1);
		int end = (pageSize > questions.size()) ? questions.size() : pageSize;
		if (!questions.isEmpty()) {
			Pageable pageable = PageRequest.of(pageNumber - 1, size);
			Page<Question> questionPage = new PageImpl<>(questions.subList((pageNumber - 1) * size, end), pageable,
					questions.size());
			paged = new Paged<>(questionPage, Paging.of(questionPage.getTotalPages(), pageNumber, size));
		}

		model.addAttribute("questions", paged);
		model.addAttribute("size", size);
		model.addAttribute("_START_", start);
		model.addAttribute("_END_", end);
		model.addAttribute("_TOTAL_", questions.size());

		return "bo/questions";
	}

	@PostMapping("")
	public String search(QuestionCriteria questionCriteria, Model model) {
		int pageNumber = 1;
		int size = questionCriteria.getSize().getValue();

		return list(pageNumber, size, questionCriteria, model);
	}

	@GetMapping("/{id}")
	public String detail(Model model, @PathVariable long id) {
		logger.info("start get question detail");
		Question question = questionService.findById(id);
		if (question == null) {
			return "bo/error/404";
		}
		MCQDto mcqDto = questionConverter.toDto(question);
		model.addAttribute("mcqDto", mcqDto);

		return targetPageByType(question.getType());
	}

	public String create(Model model) {
		logger.info("------- Start create new question -------");
		model.addAttribute("test", new Question());
		return "bo/test";
	}

	public String save(Question question) {
		logger.info("------- Start save new test -------");
		questionService.save(question);

		return "redirect:bo/questions/" + question.getId();
	}
	
	private String targetPageByType(QuestionType type) {
		switch (type) {
		case ONE_ANSWER:
			return "bo/one-ans-question";
		case MULTIPLE_ANSWER:
			return "bo/multiple-ans-question";
		case MATCHING:
			return "bo/matching-question";
		case FILLING:
			return "bo/filling-question";
		case GROUP_FILLING:
			return "bo/group-filling-question";
		case UNDERLINE:
			return "bo/underline-question";
		case YES_NO:
			return "bo/yes-no-question";
		default: // TRUE_FALSE
			return "bo/true-false-question";
		}
		
	}
	
}
