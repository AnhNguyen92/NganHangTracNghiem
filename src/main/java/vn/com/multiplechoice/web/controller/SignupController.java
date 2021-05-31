package vn.com.multiplechoice.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.UserConverterService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.request.SignUpRequest;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

    @Autowired
    private UserConverterService userConverterService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String signup(Model model) {
        model.addAttribute("newUser", new SignUpRequest());
        return "signup";
    }

    @PostMapping
    public String createNewUser(Model model, @Valid SignUpRequest signUpRequest, BindingResult bindingResult) {
        User userExists = userService.findByUsername(signUpRequest.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("userName", "error.user", "There is already a user registered with the user name provided");
        }
        if (!bindingResult.hasErrors()) {
            User user = userConverterService.toEntity(signUpRequest);
            userService.save(user);
            model.addAttribute("successMessage", "User has been registered successfully");
            model.addAttribute("user", new User());
        }
        return "login";
    }

}
