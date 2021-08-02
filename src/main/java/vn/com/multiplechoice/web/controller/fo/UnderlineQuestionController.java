package vn.com.multiplechoice.web.controller.fo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.model.QuestionAnswerDto;

@RequestMapping("/fo/questions")
@Controller
public class UnderlineQuestionController {

    private static final Logger log = LoggerFactory.getLogger(UnderlineQuestionController.class);
    
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/underline")
    public String createUnderlineQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET underline question form =====");

        mcqDto.setType(QuestionType.TRUE_FALSE);
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        if (questionAnswerDtos == null) {
            questionAnswerDtos = new ArrayList<>();
        }

        mcqDto.setQuestionAnswerDtos(questionAnswerDtos);
        model.addAttribute("mcqDto", mcqDto);

        return "fo/create-question-true-false-short";
    }

    @PostMapping("/underline")
    public String saveUnderlineQuestion(Model model, final @ModelAttribute("mcqDto") MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create underline question form =====");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        mcqDto.setUser(user);
        Question question = new Question();
        question.setContent(mcqDto.getContent());
        question.setSuggest(mcqDto.getAnswerSuggestion());
        question.setQuestionType(QuestionType.TRUE_FALSE);
        question.setUser(user);
        question.setAnswerA(mcqDto.getQuestionAnswerDtos().get(0).getAnswerContent());
        question.setAnswerB(mcqDto.getQuestionAnswerDtos().get(1).getAnswerContent());

        log.info("===== CREATE underline question form END =====");

        return "fo/index";
    }
    
}
