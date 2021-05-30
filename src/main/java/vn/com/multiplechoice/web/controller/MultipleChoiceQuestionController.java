package vn.com.multiplechoice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.multiplechoice.web.model.MultipleChoiceQuestion;

@Controller
@RequestMapping(value = "/question")
public class MultipleChoiceQuestionController {

    @GetMapping
    public String createQuestion(Model model) {
        MultipleChoiceQuestion question =  new MultipleChoiceQuestion();
    	model.addAttribute("question", question);
    	model.addAttribute("allTrueAnswers", question.getTrueAnswers());
        return "create-question";
    }

    @PostMapping
    public String saveQuestion(Model model, @RequestParam("") String question, @RequestParam("") String anser1, @RequestParam("") String anser2, @RequestParam("") String anser3, @RequestParam("") String anser4,
            @RequestParam("") String trueanswer1) {
        String target = "index";
        boolean isvalidInfo = validFormData();
        if (!isvalidInfo) {
            target = "create-question";
        }
        return target;
    }
    
    private boolean validFormData() {
        return true;
    }
}
