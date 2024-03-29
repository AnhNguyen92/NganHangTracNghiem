package vn.com.multiplechoice.web.controller.fo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.UserRequestService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.UserRequest;
import vn.com.multiplechoice.dao.model.enums.UserRequestStatus;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

@Controller
@RequestMapping("/fo/contact")
public class ContactController {
    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private OnlineUserUtil onlineUserUtil;
    
    @Autowired
    private UserRequestService userRequestService;
    
    @GetMapping
    public String contact(Model model) {
        logger.info("START get contact");
        model.addAttribute("userRequest", new UserRequest());
        
        return "fo/contact";
    }
    
    @PostMapping
    public String sendRequest(Model model, @ModelAttribute("userRequest") UserRequest userRequest) {
        logger.info("START check request");
        User user = onlineUserUtil.getOnlineUser();
        userRequest.setCreateDate(new Date());
        userRequest.setCreator(user);
        userRequest.setStatus(UserRequestStatus.UN_READ);
        userRequestService.save(userRequest);
        
        model.addAttribute("message", "Yêu cầu đã được gửi tới admin!");
        
        return "fo/contact";
    }

}
