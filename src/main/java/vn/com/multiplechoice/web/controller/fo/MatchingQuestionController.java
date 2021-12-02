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

@RequestMapping("/fo/questions/matching")
@Controller
public class MatchingQuestionController {
    private static final Logger log = LoggerFactory.getLogger(MatchingQuestionController.class);

    private static final String FO_CREATE_MATCHING_QUESTION = "fo/create-matching-question";
    private static final String[] ANSWER_LABELS = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };
    private static final String MCQ_DTO = "mcqDto";

    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private QuestionConverter questionConverter;

    @RequestMapping("")
    public String creatematchingQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET matching question form =====");

        mcqDto.setType(QuestionType.MATCHING);
        List<QuestionAnswerDto> leftAnswerDtos = new ArrayList<>();
        List<QuestionAnswerDto> rightAnswerDtos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
            questionAnswerDto.setAnswerLabel((i + 1) + "");
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(true);
            questionAnswerDto.setScore(0);
            leftAnswerDtos.add(questionAnswerDto);
        }
        for (int i = 0; i < 4; i++) {
            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(false);
            rightAnswerDtos.add(questionAnswerDto);
        }
        mcqDto.setLeftAnswerDtos(leftAnswerDtos);
        mcqDto.setRightAnswerDtos(rightAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_MATCHING_QUESTION;
    }

    @RequestMapping(value = "", params = { "add-left-answer" })
    public String addLeftAnswer(Model model, final MCQDto mcqDto, final BindingResult result) {
        List<QuestionAnswerDto> leftAnswerDtos = mcqDto.getLeftAnswerDtos();
        for (int i = 0; i < leftAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = leftAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel("" + (i + 1));
            questionAnswerDto.setOrder(i);
        }
        QuestionAnswerDto newItem = new QuestionAnswerDto();
        newItem.setAnswerLabel((leftAnswerDtos.size() + 1) + "");
        newItem.setOrder(leftAnswerDtos.size());
        newItem.setLeftSide(true);
        leftAnswerDtos.add(newItem);
        mcqDto.setLeftAnswerDtos(leftAnswerDtos);
        
        List<QuestionAnswerDto> rightAnswerDtos = mcqDto.getRightAnswerDtos();
        for (int i = 0; i < rightAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = rightAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(false);
        }
        mcqDto.setRightAnswerDtos(rightAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_MATCHING_QUESTION;
    }

    @RequestMapping(value = "", params = { "remove-left-answer" })
    public String removeLeftAnswer(Model model, MCQDto mcqDto, final BindingResult result, final HttpServletRequest req) {
        List<QuestionAnswerDto> leftAnswerDtos = mcqDto.getLeftAnswerDtos();
        String indexStr = req.getParameter("remove-left-answer");
        int index = Integer.parseInt(indexStr);
        leftAnswerDtos.remove(index);
        for (int i = 0; i < leftAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = leftAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel("" + (i + 1));
            questionAnswerDto.setOrder(i);
        }
        mcqDto.setLeftAnswerDtos(leftAnswerDtos);
        
        List<QuestionAnswerDto> rightAnswerDtos = mcqDto.getRightAnswerDtos();
        for (int i = 0; i < rightAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = rightAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(false);
        }
        mcqDto.setRightAnswerDtos(rightAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_MATCHING_QUESTION;
    }

    @RequestMapping(value = "", params = { "add-right-answer" })
    public String addRightAnswer(Model model, final MCQDto mcqDto, final BindingResult result) {
        List<QuestionAnswerDto> leftAnswerDtos = mcqDto.getLeftAnswerDtos();
        for (int i = 0; i < leftAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = leftAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel("" + (i + 1));
            questionAnswerDto.setOrder(i);
        }
        mcqDto.setLeftAnswerDtos(leftAnswerDtos);
        
        List<QuestionAnswerDto> rightAnswerDtos = mcqDto.getRightAnswerDtos();
        for (int i = 0; i < rightAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = rightAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(false);
        }
        QuestionAnswerDto newItem = new QuestionAnswerDto();
        newItem.setAnswerLabel(ANSWER_LABELS[rightAnswerDtos.size()]);
        newItem.setOrder(rightAnswerDtos.size());
        newItem.setLeftSide(false);
        rightAnswerDtos.add(newItem);
        mcqDto.setRightAnswerDtos(rightAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_MATCHING_QUESTION;
    }

    @RequestMapping(value = "", params = { "remove-right-answer" })
    public String removeRightAnswer(Model model, MCQDto mcqDto, final BindingResult result, final HttpServletRequest req) {
        List<QuestionAnswerDto> leftAnswerDtos = mcqDto.getLeftAnswerDtos();
        
        for (int i = 0; i < leftAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = leftAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel("" + (i + 1));
            questionAnswerDto.setOrder(i);
        }
        mcqDto.setLeftAnswerDtos(leftAnswerDtos);
        
        List<QuestionAnswerDto> rightAnswerDtos = mcqDto.getRightAnswerDtos();
        String indexStr = req.getParameter("remove-right-answer");
        int index = Integer.parseInt(indexStr);
        rightAnswerDtos.remove(index);
        for (int i = 0; i < rightAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = rightAnswerDtos.get(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(false);
        }
        mcqDto.setRightAnswerDtos(rightAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_MATCHING_QUESTION;
    }

    @PostMapping("")
    public String saveMatchingQuestion(Model model, final @ModelAttribute("mcqDto") MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create matching question form =====");
        
        Question question = questionConverter.toEntity(mcqDto);
        questionService.save(question);

        log.info("===== CREATE matching question form END =====");

        return "redirect:/fo/index";
    }

}
