package vn.com.multiplechoice.web.controller.fo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.QuestionConverter;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.model.QuestionAnswerDto;

@RequestMapping("/fo/questions")
@Controller
public class TrueFalseQuestionController {

    private static final Logger log = LoggerFactory.getLogger(TrueFalseQuestionController.class);
    
    private static final String FO_INDEX = "fo/index";
    private static final String FO_CREATE_QUESTION_TRUE_FALSE = "/fo/create-true-false-question";
    private static final String[] ANSWER_LABELS =  new String[] {"A", "B", "C", "D", "E", "F", "G", "H"};
    private static final String MCQ_DTO = "mcqDto";
    private static final String REMOVE_ANSWER = "remove-answer";

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private QuestionConverter questionConverter;
    
    @RequestMapping("/yes-no")
    public String createDefaultTrueFalseQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET yes no question form =====");
        mcqDto.setType(QuestionType.YES_NO);
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        if (questionAnswerDtos == null) {
            questionAnswerDtos = new ArrayList<>();
        }

        QuestionAnswerDto trueAnswerDto = new QuestionAnswerDto();
        trueAnswerDto.setAnswerLabel("A");
        trueAnswerDto.setOrder(0);
        trueAnswerDto.setTrueAnswer(true);
        trueAnswerDto.setAnswerContent("Đúng");
        questionAnswerDtos.add(trueAnswerDto);

        QuestionAnswerDto falseAnswerDto = new QuestionAnswerDto();
        falseAnswerDto.setAnswerLabel("B");
        falseAnswerDto.setOrder(1);
        falseAnswerDto.setTrueAnswer(false);
        falseAnswerDto.setAnswerContent("Sai");
        questionAnswerDtos.add(falseAnswerDto);

        mcqDto.setQuestionAnswerDtos(questionAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return "fo/create-yes-no-question";
    }

    @PostMapping("/yes-no")
    public String saveDefaultTrueFalseQuestion(Model model, final @ModelAttribute("mcqDto") MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create yes no question form =====");
        Question question = questionConverter.toEntity(mcqDto);
        questionService.save(question);
        log.info("===== CREATE -yes no question form END =====");

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
            questionAnswerDto.setScore(0);
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
    	Question question = questionConverter.toEntity(mcqDto);
        questionService.save(question);
        
        log.info("===== CREATE true false answer question form END =====");

        return FO_INDEX;
    }
    
}
