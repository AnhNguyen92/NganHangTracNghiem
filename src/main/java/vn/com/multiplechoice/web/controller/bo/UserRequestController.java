package vn.com.multiplechoice.web.controller.bo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.UserRequestService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.UserRequest;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

@Controller
@RequestMapping("/bo/user-requests")
public class UserRequestController {
    private static final Logger log = LoggerFactory.getLogger(UserRequestController.class);

    @Autowired
    private OnlineUserUtil onlineUserUtil;

    @Autowired
    private UserRequestService userRequestService;

    @GetMapping(value = { "" })
    public String getUserRequests(Model model) {
        log.info("Enter getUserRequests");
        List<UserRequest> userRequests = userRequestService.findAll();
        model.addAttribute("userRequests", userRequests);

        return "user-requests";
    }

    @GetMapping("/update")
    public String updateUserRequest(Model model, UserRequest userRequest) {
        log.info("Enter updateUserRequest");
        User user = onlineUserUtil.getOnlineUser();
        userRequest.setViewPerson(user);
        userRequestService.save(userRequest);
        
        return "bo/user-requests";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable Long id) {
        log.info("Enter UserRequest detail");
        UserRequest userRequest = userRequestService.findById(id);
        model.addAttribute("userRequest", userRequest);

        return "bo/user-request" ;
    }

}
