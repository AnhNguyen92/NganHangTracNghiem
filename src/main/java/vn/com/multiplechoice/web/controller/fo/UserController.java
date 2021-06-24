package vn.com.multiplechoice.web.controller.fo;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    @GetMapping("/user")
    public String detail() {
        return "fo/user";
    }

    @GetMapping("/fo/forgot-password")
    public String forgotPassword() {
        return "fo/forgot-password";
    }

    @PostMapping("/fo/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        User user = userService.findByEmail(email);
        
        return "fo/reset-password";
    }
    
    @GetMapping("/fo/reset-password")
    public String resetPassword() {
        return "fo/forgot-password";
    }

    @PostMapping("/fo/reset-password")
    public String processResetPassword() {
        return "fo/reset-password";
    }
    
}
