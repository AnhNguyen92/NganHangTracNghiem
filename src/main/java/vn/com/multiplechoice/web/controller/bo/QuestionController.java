package vn.com.multiplechoice.web.controller.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.model.Question;

@Controller
@RequestMapping("/bo/questions")
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private QuestionService questionService;
    
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = { "", "/list" })
//    public String getQuestions(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
//            @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {
	public String list(Model model) {
    	logger.info("start get list question");
		int pageNumber = 1;
		int size = 5;
		List<Question> questions = questionService.findAll();
		model.addAttribute("questions", questions);

		return "bo/questions";
	}

    @GetMapping("/{id}")
	public String detail(Model model, @PathVariable long id) {
    	logger.info("start get question detail");
    	Question question = questionService.findOne(id);
    	if (question == null) {
    		return "bo/error/404";
    	}
		model.addAttribute("question", question);

		return "bo/question";
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
}
