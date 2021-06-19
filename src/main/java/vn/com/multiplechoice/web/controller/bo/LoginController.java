package vn.com.multiplechoice.web.controller.bo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.web.form.LoginForm;

@Controller(value = "boLoginController")
@RequestMapping("/bo/login")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "bo/login";
    }
    
    @PostMapping
    public String index(Model model, LoginForm loginForm) {
        log.info("Login BO, username: {}", loginForm.getUsername());

        return "bo/user-list";
    }
}
