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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.model.QuestionAnswerDto;

@RequestMapping("/fo/questions")
@Controller
public class MatchingQuestionController {

    private static final String FO_CREATE_QUESTION_UNDERLINE = "fo/create-matching-question";
    private static final Logger log = LoggerFactory.getLogger(MatchingQuestionController.class);
    private static final String[] ANSWER_LABELS = new String[] { "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D", "Đáp án E", "Đáp án F", "Đáp án G",
            "Đáp án H" };
    private static final String MCQ_DTO = "mcqDto";

    @Autowired
    private UserService userService;

    @RequestMapping("/matching")
    public String createUnderlineQuestion(Model model, MCQDto mcqDto) {
        log.info("===== GET underline question form =====");

        mcqDto.setType(QuestionType.MATCHING);
        List<QuestionAnswerDto> leftAnswerDtos = new ArrayList<>();
        List<QuestionAnswerDto> rightAnswerDtos = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
            questionAnswerDto.setAnswerLabel( (i + 1) + "");
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(true);
            leftAnswerDtos.add(questionAnswerDto);
        }
        for (int i = 0; i < 4; i++) {
            QuestionAnswerDto questionAnswerDto = new QuestionAnswerDto();
            questionAnswerDto.setAnswerLabel(ANSWER_LABELS[i]);
            questionAnswerDto.setOrder(i);
            questionAnswerDto.setLeftSide(true);
            rightAnswerDtos.add(questionAnswerDto);
        }
        mcqDto.setLeftAnswerDtos(leftAnswerDtos);
        mcqDto.setRightAnswerDtos(rightAnswerDtos);
        model.addAttribute(MCQ_DTO, mcqDto);

        return FO_CREATE_QUESTION_UNDERLINE;
    }

    @RequestMapping(value = "/matching", params = { "add-answer" })
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

        return FO_CREATE_QUESTION_UNDERLINE;
    }

    @RequestMapping(value = "/matching", params = { "remove-answer" })
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

        return FO_CREATE_QUESTION_UNDERLINE;
    }

    @PostMapping("/matching")
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
