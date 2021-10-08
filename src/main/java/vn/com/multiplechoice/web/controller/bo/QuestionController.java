package vn.com.multiplechoice.web.controller.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.multiplechoice.business.service.QuestionService;

@Controller
@RequestMapping("/bo/questions")
public class QuestionController {

    
    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    private final QuestionService questionService;
    
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String getQuestions(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "size", required = false, defaultValue = "5") int size, Model model) {
        model.addAttribute("questions", questionService.getPage(pageNumber, size));
        return "questions";
    }
 
}
