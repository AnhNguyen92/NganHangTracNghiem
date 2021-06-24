package vn.com.multiplechoice.web.controller.fo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user")
    public String detail() {
        return "fo/user";
    }

    @GetMapping("/fo/forgot-password")
    public String forgotPass() {
        return "fo/forgot-password";
    }

}
