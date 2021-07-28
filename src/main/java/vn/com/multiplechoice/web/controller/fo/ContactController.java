package vn.com.multiplechoice.web.controller.fo;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.UserRequest;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

@Controller
@RequestMapping("/fo/contact")
public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private OnlineUserUtil onlineUserUtil;
    
    @GetMapping
    public String contact(Model model) {
        logger.info("START get contact");
        return "fo/contact";
    }
    
    @PostMapping
    public String sendRequest(Model model, String message) {
        logger.info("START check request");
        User user = onlineUserUtil.getOnlineUser();
        UserRequest userRequest = new UserRequest();
        userRequest.setContent(message);
        userRequest.setCreateDate(LocalDate.now());
        userRequest.setUser(user);
        
        
        model.addAttribute("message", "Yêu cầu đã được gửi tới admin!");
        
        return "fo/contact";
    }

}
