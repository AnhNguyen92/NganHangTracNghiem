package vn.com.multiplechoice.web.controller.bo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bo/login")
public class LoginController {

    @GetMapping
    public String login(Model model) {
        return "bo/login";
    }

}
