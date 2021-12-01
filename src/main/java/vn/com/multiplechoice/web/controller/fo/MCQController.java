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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.QuestionConverter;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.model.QuestionAnswerDto;

@Controller
@RequestMapping(value = "/fo/questions")
public class MCQController {
	private static final Logger log = LoggerFactory.getLogger(MCQController.class);

	private static final String REMOVE_ANSWER = "remove-answer";
    private static final String REDIRECT_FO_INDEX = "redirect:/fo/index";
    private static final String MCQ_DTO = "mcqDto";
    private static final String FO_CREATE_ONE_ANS_QUESTION = "fo/create-one-ans-question";
    private static final String FO_CREATE_MULTIPLE_ANS_QUESTION = "fo/create-multiple-ans-question";
    private static final String[] ANSWER_LABELS = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private QuestionConverter questionConverter;
    
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

        return FO_CREATE_ONE_ANS_QUESTION;
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

        return FO_CREATE_ONE_ANS_QUESTION;
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

        return FO_CREATE_ONE_ANS_QUESTION;
    }

    @PostMapping("/one-ans")
    public String saveOneAnswerQuestion(Model model, final @ModelAttribute(MCQ_DTO) MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create one answer question form =====");
        Question question = questionConverter.toEntity(mcqDto);

        questionService.save(question);

        log.info("===== CREATE one answer question form END =====");

        return REDIRECT_FO_INDEX;
    }

    @GetMapping("/one-ans/{id}")
    public String oneAnswerDetail(Model model, @PathVariable long id) {
    	Question question = questionService.findOne(id);
    	MCQDto mcqDto = questionConverter.toDto(question);
    	model.addAttribute(MCQ_DTO, mcqDto);
    	
    	return FO_CREATE_ONE_ANS_QUESTION;
    }
    
    @GetMapping("/fragment/{id}")
    public String questionFragmentDetail(Model model, @PathVariable long id) {
    	Question question = questionService.findOne(id);
    	model.addAttribute("question", question);
    	
    	return "fragments/fo/question";
    }
    
    @RequestMapping("/multiple-ans")
    public String createMultipleAnswerQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET multiple answer question form =====");
        mcqDto.setType(QuestionType.ONE_ANSWER);
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

        return FO_CREATE_MULTIPLE_ANS_QUESTION;
    }

    @RequestMapping(value = "/multiple-ans", params = { "add-answer" })
    public String addAnswerMultipleQuestion(Model model, final MCQDto mcqDto, final BindingResult result) {
    	log.info("===== ADD answer for multiple answer question form =====");
        for (int i = 0; i < mcqDto.getQuestionAnswerDtos().size(); i++) {
            QuestionAnswerDto questionAnswerDto = mcqDto.getQuestionAnswerDtos().get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        QuestionAnswerDto newQuestionAnswerDto = new QuestionAnswerDto();
        newQuestionAnswerDto.setAnswerLabel(ANSWER_LABELS[mcqDto.getQuestionAnswerDtos().size()]);
        mcqDto.getQuestionAnswerDtos().add(newQuestionAnswerDto);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_MULTIPLE_ANS_QUESTION;
    }

    @RequestMapping(value = "/multiple-ans", params = { REMOVE_ANSWER })
    public String removeAnswerMultipleQuestion(Model model, MCQDto mcqDto, final BindingResult result, final HttpServletRequest req) {
    	log.info("===== REMOVE answer for multiple answer question form =====");
    	List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
        String index = req.getParameter(REMOVE_ANSWER);
        questionAnswerDtos.remove(questionAnswerDtos.get(Integer.parseInt(index)));
        for (int i = 0; i < questionAnswerDtos.size(); i++) {
            QuestionAnswerDto questionAnswerDto = questionAnswerDtos.get(i);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
        }
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_MULTIPLE_ANS_QUESTION;
    }

    @PostMapping("/multiple-ans")
    public String saveMultipleAnswerQuestion(Model model, final @ModelAttribute(MCQ_DTO) MCQDto mcqDto, final BindingResult result) {
        log.info("===== START create multiple answer question form =====");
        Question question = questionConverter.toEntity(mcqDto);
        
        questionService.save(question);
        log.info("===== CREATE multiple answer question form END =====");

        return REDIRECT_FO_INDEX;
    }

    
}
