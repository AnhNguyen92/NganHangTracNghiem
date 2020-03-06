package vn.com.ids.multiplechoice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/question")
public class MultipleChoiceQuestionController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String createQuestion() {
        return "create-question";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String f(Model model, @RequestParam("") String question, @RequestParam("") String anser1, @RequestParam("") String anser2, @RequestParam("") String anser3, @RequestParam("") String anser4,
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
