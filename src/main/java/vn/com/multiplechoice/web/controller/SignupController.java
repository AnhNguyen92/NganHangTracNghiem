package vn.com.multiplechoice.web.controller;

import java.time.LocalDate;
import java.time.Period;

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
        if (bindingResult.hasErrors()) {
            model.addAttribute("newUser", signUpRequest);
            return "signup";
        }
        User userExists = userService.findByUsername(signUpRequest.getUsername());
        if (userExists != null) {
            bindingResult.rejectValue("userName", "error.user", "Tài khoản đã được đăng ký");
        }
        // kiểm tra ngày sinh xem thành viên đã đủ 18 tuổi
        if (signUpRequest.getBirthday() != null) {
            LocalDate birthDay = signUpRequest.getBirthday();
            Period period = Period.between(LocalDate.now(), birthDay);
            int years = Math.abs(period.getYears());
            if (years < 18) {
                bindingResult.rejectValue("birthday", "error.birthday", "Bạn chưa đủ 18 tuổi.");
            }
        }
        // kiểm tra mật khẩu nhập có giống nhau không
        if (!signUpRequest.getPassword().equals(signUpRequest.getRePassword())) {
            bindingResult.rejectValue("rePassword", "error.rePassword", "Mật khẩu nhập lại không khớp!");
        }
        if (!bindingResult.hasErrors()) {
            User user = userConverterService.toEntity(signUpRequest);
            userService.save(user);
            // send verify email here
            model.addAttribute("successMessage", "Đăng ký thành công!");
            model.addAttribute("newUser", new SignUpRequest());
        }
        return "signup";
    }

}
