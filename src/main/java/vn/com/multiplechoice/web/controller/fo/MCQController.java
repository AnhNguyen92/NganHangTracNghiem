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

@Controller
@RequestMapping(value = "/fo/questions")
public class MCQController {
    private static final String REMOVE_ANSWER = "remove-answer";
    private static final String FO_INDEX = "fo/index";
    private static final Logger log = LoggerFactory.getLogger(MCQController.class);
    private static final String MCQ_DTO = "mcqDto";
    private static final String FO_CREATE_QUESTION_TRUE_FALSE = "/fo/create-question-true-false";
    private static final String FO_CREATE_QUESTION_ONE_ANS = "fo/create-question-one-ans";
    private static final String FO_CREATE_QUESTION_MULTIPLE_ANS = "fo/create-question-multiple-ans";
    private static final String[] ANSWER_LABELS =  new String[] {"Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D", "Đáp án E", "Đáp án F", "Đáp án G", "Đáp án H"};
    
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;
    
    @RequestMapping("/one-ans")
    public String createOneAnswerQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET one answer question form =====");

        mcqDto.setType(QuestionType.ONE_ANSWER);
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        if (questionAnswerDtos == null) {
            questionAnswerDtos = new ArrayList<>();
        }
        for (int i = 0; i < 4; i++) {
            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDtos.add(questionAnswerDto);
        }
        mcqDto.setQuestionAnswerDtos(questionAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_ONE_ANS;
    }

    @RequestMapping(value = "/one-ans", params = { "add-answer" })
    public String addAnswer(Model model, final MCQDto mcqDto, final BindingResult result) {
        for (int i = 0; i < mcqDto.getQuestionAnswerDtos().size(); i++) {
            QuestionAnswerDto questionAnswerDto = mcqDto.getQuestionAnswerDtos().get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        QuestionAnswerDto newQuestionAnswerDto = new QuestionAnswerDto();
        newQuestionAnswerDto.setAnswerLabel(ANSWER_LABELS[mcqDto.getQuestionAnswerDtos().size()]);
        mcqDto.getQuestionAnswerDtos().add(newQuestionAnswerDto);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_ONE_ANS;
    }

    @RequestMapping(value = "/one-ans", params = { REMOVE_ANSWER })
    public String removeAnswer(Model model, MCQDto mcqDto, final BindingResult result, final HttpServletRequest req) {
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        String index = req.getParameter(REMOVE_ANSWER);
        questionAnswerDtos.remove(questionAnswerDtos.get(Integer.parseInt(index)));
        for (int i = 0; i < questionAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = questionAnswerDtos.get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        model.addAttribute(MCQ_DTO, mcqDto);
        
        return FO_CREATE_QUESTION_ONE_ANS;
    }

    @PostMapping("/one-ans")
    public String saveOneAnswerQuestion(Model model, final @ModelAttribute(MCQ_DTO) MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create one answer question form =====");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        mcqDto.setUser(user);
        Question question = new Question();
        question.setContent(mcqDto.getContent());
        question.setSuggest(mcqDto.getAnswerSuggestion());
        question.setQuestionType(QuestionType.ONE_ANSWER);
        question.setUser(user);
        question.setAnswerA(mcqDto.getQuestionAnswerDtos().get(0).getAnswerContent());
        question.setAnswerB(mcqDto.getQuestionAnswerDtos().get(1).getAnswerContent());
        question.setAnswerC(mcqDto.getQuestionAnswerDtos().get(2).getAnswerContent());
        question.setAnswerD(mcqDto.getQuestionAnswerDtos().get(3).getAnswerContent());
        
        questionService.save(question);
        
        log.info("===== CREATE one answer question form END =====");

        return FO_INDEX;
    }

    @RequestMapping("/true-false")
    public String createTrueFalseQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET true false question form =====");

        mcqDto.setType(QuestionType.TRUE_FALSE);
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        if (questionAnswerDtos == null) {
            questionAnswerDtos = new ArrayList<>();
        }
        for (int i = 0; i < 4; i++) {
            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDtos.add(questionAnswerDto);
        }
        mcqDto.setQuestionAnswerDtos(questionAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_TRUE_FALSE;
    }
    
    @RequestMapping(value = "/true-false", params = { "add-answer" })
    public String addTrueFalseAnswer(Model model, final MCQDto mcqDto, final BindingResult result) {
        for (int i = 0; i < mcqDto.getQuestionAnswerDtos().size(); i++) {
            QuestionAnswerDto questionAnswerDto = mcqDto.getQuestionAnswerDtos().get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        QuestionAnswerDto newQuestionAnswerDto = new QuestionAnswerDto();
        newQuestionAnswerDto.setAnswerLabel(ANSWER_LABELS[mcqDto.getQuestionAnswerDtos().size()]);
        mcqDto.getQuestionAnswerDtos().add(newQuestionAnswerDto);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_TRUE_FALSE;
    }

    @RequestMapping(value = "/true-false", params = { REMOVE_ANSWER })
    public String removeTrueFalseAnswer(Model model, MCQDto mcqDto, final BindingResult result, final HttpServletRequest req) {
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        String index = req.getParameter(REMOVE_ANSWER);
        questionAnswerDtos.remove(questionAnswerDtos.get(Integer.parseInt(index)));
        for (int i = 0; i < questionAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = questionAnswerDtos.get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        model.addAttribute(MCQ_DTO, mcqDto);
        
        return FO_CREATE_QUESTION_TRUE_FALSE;
    }

    
    @PostMapping("/true-false")
    public String saveTrueFalseQuestion(Model model, final @ModelAttribute(MCQ_DTO) MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create true false question form =====");
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
//        question.setAnswerC(mcqDto.getQuestionAnswerDtos().get(2).getAnswerContent());
//        question.setAnswerD(mcqDto.getQuestionAnswerDtos().get(3).getAnswerContent());
        
//        questionService.save(question);
        
        log.info("===== CREATE true false answer question form END =====");

        return FO_INDEX;
    }
    
    @RequestMapping("/multiple-ans")
    public String createMultipleAnswerQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET one answer question form =====");

        mcqDto.setType(QuestionType.ONE_ANSWER);
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        if (questionAnswerDtos == null) {
            questionAnswerDtos = new ArrayList<>();
        }
        for (int i = 0; i < 4; i++) {
            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDtos.add(questionAnswerDto);
        }
        mcqDto.setQuestionAnswerDtos(questionAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_MULTIPLE_ANS;
    }

    @RequestMapping(value = "/multiple-ans", params = { "add-answer" })
    public String addAnswerMultipleQuestion(Model model, final MCQDto mcqDto, final BindingResult result) {
        for (int i = 0; i < mcqDto.getQuestionAnswerDtos().size(); i++) {
            QuestionAnswerDto questionAnswerDto = mcqDto.getQuestionAnswerDtos().get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        QuestionAnswerDto newQuestionAnswerDto = new QuestionAnswerDto();
        newQuestionAnswerDto.setAnswerLabel(ANSWER_LABELS[mcqDto.getQuestionAnswerDtos().size()]);
        mcqDto.getQuestionAnswerDtos().add(newQuestionAnswerDto);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_MULTIPLE_ANS;
    }

    @RequestMapping(value = "/multiple-ans", params = { REMOVE_ANSWER })
    public String removeAnswerMultipleQuestion(Model model, MCQDto mcqDto, final BindingResult result, final HttpServletRequest req) {
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        String index = req.getParameter(REMOVE_ANSWER);
        questionAnswerDtos.remove(questionAnswerDtos.get(Integer.parseInt(index)));
        for (int i = 0; i < questionAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = questionAnswerDtos.get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        model.addAttribute(MCQ_DTO, mcqDto);
        
        return FO_CREATE_QUESTION_MULTIPLE_ANS;
    }

    @PostMapping("/multiple-ans")
    public String saveMultipleAnswerQuestion(Model model, final @ModelAttribute(MCQ_DTO) MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create one answer question form =====");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        mcqDto.setUser(user);
        Question question = new Question();
        question.setContent(mcqDto.getContent());
        question.setSuggest(mcqDto.getAnswerSuggestion());
        question.setQuestionType(QuestionType.MULTIPLE_ANSWER);
        question.setUser(user);
        question.setAnswerA(mcqDto.getQuestionAnswerDtos().get(0).getAnswerContent());
        question.setAnswerB(mcqDto.getQuestionAnswerDtos().get(1).getAnswerContent());
        question.setAnswerC(mcqDto.getQuestionAnswerDtos().get(2).getAnswerContent());
        question.setAnswerD(mcqDto.getQuestionAnswerDtos().get(3).getAnswerContent());
        
//        questionService.save(question);
        
        log.info("===== CREATE one answer question form END =====");

        return FO_INDEX;
    }
    
}
