package vn.com.multiplechoice.web.controller.fo;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
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
public class UnderlineQuestionController {

    private static final String FO_CREATE_QUESTION_UNDERLINE = "fo/create-underline-question";
    private static final Logger log = LoggerFactory.getLogger(UnderlineQuestionController.class);
    private static final String[] ANSWER_LABELS = new String[] { "A", "B", "C", "D", "E", "F", "G",
            "H" };
    private static final String MCQ_DTO = "mcqDto";

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionConverter questionConverter;

    @RequestMapping("/underline")
    public String createUnderlineQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET underline question form =====");

        mcqDto.setType(QuestionType.UNDERLINE);
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        if (questionAnswerDtos == null) {
            questionAnswerDtos = new ArrayList<>();
        }
        
        mcqDto.setQuestionAnswerDtos(questionAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_UNDERLINE;
    }

    @RequestMapping(value = "/underline", params = { "add-answer" })
    public String addAnswer(Model model, MCQDto mcqDto, final BindingResult result) {
        List<QuestionAnswerDto> answerDtos = mcqDto.getQuestionAnswerDtos();
        if (CollectionUtils.isEmpty(mcqDto.getQuestionAnswerDtos())) {
            answerDtos = new ArrayList<>();
        }
        for (int i = 0; i < answerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = mcqDto.getQuestionAnswerDtos().get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }

        QuestionAnswerDto newQuestionAnswerDto = new QuestionAnswerDto();
        newQuestionAnswerDto.setAnswerLabel(ANSWER_LABELS[answerDtos.size()]);
        answerDtos.add(newQuestionAnswerDto);
        mcqDto.setQuestionAnswerDtos(answerDtos);

        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_UNDERLINE;
    }

    @RequestMapping(value = "/underline", params = { "remove-answer" })
    public String removeAnswer(Model model, MCQDto mcqDto, BindingResult result, final HttpServletRequest req) {
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        String index = req.getParameter("remove-answer");
        questionAnswerDtos.remove(questionAnswerDtos.get(Integer.parseInt(index)));
        for (int i = 0; i < questionAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = questionAnswerDtos.get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_UNDERLINE;
    }

    @PostMapping("/underline")
    public String saveUnderlineQuestion(Model model, @ModelAttribute("mcqDto") MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create underline question form =====");
        
        Question question = questionConverter.toEntity(mcqDto);
        questionService.save(question);
        
        log.info("===== CREATE underline question form END =====");

        return "redirect:/fo/index";
    }

}
