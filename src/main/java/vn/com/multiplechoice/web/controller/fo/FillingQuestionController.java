
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
public class FillingQuestionController {

    private static final String FO_CREATE_FILLING_QUESTION = "fo/create-flling-question";
    private static final Logger log = LoggerFactory.getLogger(FillingQuestionController.class);
    private static final String[] ANSWER_LABELS = new String[] { "A", "B", "C", "D", "E", "F", "G",
            "H" };
    private static final String MCQ_DTO = "mcqDto";

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionConverter questionConverter;

    @RequestMapping("/filling")
    public String createFillingQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET filling question form =====");

        mcqDto.setType(QuestionType.FILLING);
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

        return FO_CREATE_FILLING_QUESTION;
    }

    @RequestMapping(value = "/filling", params = { "add-answer" })
    public String addAnswer(Model model, final MCQDto mcqDto, final BindingResult result) {
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

        return FO_CREATE_FILLING_QUESTION;
    }

    @RequestMapping(value = "/filling", params = { "remove-answer" })
    public String removeAnswer(Model model, MCQDto mcqDto, final BindingResult result, final HttpServletRequest req) {
        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        String index = req.getParameter("remove-answer");
        questionAnswerDtos.remove(questionAnswerDtos.get(Integer.parseInt(index)));
        for (int i = 0; i < questionAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = questionAnswerDtos.get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_FILLING_QUESTION;
    }

    @PostMapping("/filling")
    public String saveFillingQuestion(Model model, final @ModelAttribute("mcqDto") MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create filling question form =====");
        
        Question question = questionConverter.toEntity(mcqDto);
        questionService.save(question);

        log.info("===== CREATE filling question form END =====");

        return "redirect:/fo/index";
    }

    // request mapping for group-filling question
    @RequestMapping("/group-filling")
    public String createGroupFillingQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET group-filling question form =====");

        mcqDto.setType(QuestionType.GROUP_FILLING);
        List<QuestionAnswerDto> leftAnswerDtos = new ArrayList<>();
        List<QuestionAnswerDto> rightAnswerDtos = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
//            questionAnswerDto.setAnswerLabel((i + 1) + "");
//            questionAnswerDto.setOrder(i);
//            questionAnswerDto.setLeftSide(true);
//            leftAnswerDtos.add(questionAnswerDto);
//        }
//        for (int i = 0; i < 4; i++) {
//            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
//            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
//            questionAnswerDto.setOrder(i);
//            questionAnswerDto.setLeftSide(false);
//            rightAnswerDtos.add(questionAnswerDto);
//        }
        mcqDto.setLeftAnswerDtos(leftAnswerDtos);
        mcqDto.setRightAnswerDtos(rightAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return "fo/create-group-filling-question";
    }
    @PostMapping("/group-filling")
    public String saveGroupFillingQuestion(Model model, final @ModelAttribute("mcqDto") MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create group filling question form =====");
        
        Question question = questionConverter.toEntity(mcqDto);
        questionService.save(question);

        log.info("===== CREATE group filling question form END =====");

        return "redirect:/fo/index";
    }
}
