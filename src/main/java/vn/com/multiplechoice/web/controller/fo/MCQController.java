package vn.com.multiplechoice.web.controller.fo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.model.MCQDto;

@Controller
@RequestMapping(value = "/fo/questions")
public class MCQController {
    private static final Logger log = LoggerFactory.getLogger(MCQController.class);

    @Autowired
    private UserService userService;
    
    @GetMapping("/one-ans")
    public String createOneAnswerQuestion(Model model) {
        log.info("===== GET one answer question form =====");
        MCQDto mcqDto = new MCQDto();
        model.addAttribute("mcqDto", mcqDto);

        return "fo/create-question-one-ans";
    }

    @PostMapping("/one-ans/save")
    public String saveOneAnswerQuestion(Model model, @ModelAttribute("mcqDto") MCQDto mcqDto) {
        log.info("===== START create one answer question form =====");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        mcqDto.setUser(user);
        
        log.info("===== CREATE one answer question form END =====");
        
        return "fo/index";
    }

}
