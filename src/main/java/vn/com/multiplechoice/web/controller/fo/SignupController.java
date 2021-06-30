package vn.com.multiplechoice.web.controller.fo;

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
import vn.com.multiplechoice.web.form.SignUpForm;

@Controller
@RequestMapping("/fo/signup")
public class SignupController {

    private static final String SIGNUP_FORM = "signUpForm";
    private static final String SIGNUP = "fo/signup";

    @Autowired
    private UserConverterService userConverterService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String signup(Model model) {
        model.addAttribute(SIGNUP_FORM, new SignUpForm());
        
        return SIGNUP;
    }

    @PostMapping
    public String createUser(Model model, @Valid SignUpForm signUpRequest, BindingResult bindingResult) {
        User userExists = userService.findByUsername(signUpRequest.getUsername());
        String errorCode = "error.signUpRequest";
        if (userExists != null) {
            bindingResult.rejectValue("username", errorCode, "Tài khoản đã được đăng ký");
        }
        // kiểm tra ngày sinh xem thành viên đã đủ 18 tuổi
        if (signUpRequest.getBirthday() != null) {
            LocalDate birthDay = signUpRequest.getBirthday();
            Period period = Period.between(LocalDate.now(), birthDay);
            int years = Math.abs(period.getYears());
            if (years < 18) {
                bindingResult.rejectValue("birthday", errorCode, "Bạn chưa đủ 18 tuổi.");
            }
        }
        // kiểm tra mật khẩu nhập có giống nhau không
        if (!signUpRequest.getPassword().equals(signUpRequest.getRePassword())) {
            bindingResult.rejectValue("rePassword", errorCode, "Mật khẩu nhập lại không khớp!");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute(SIGNUP_FORM, signUpRequest);
        } else {
            User user = userConverterService.toEntity(signUpRequest);
            userService.save(user);
            // send verify email here
            model.addAttribute(SIGNUP_FORM, new SignUpForm());
            model.addAttribute("successMessage", "Đăng ký thành công!");
            
            // send verify email
        }

        return SIGNUP;
    }

}
