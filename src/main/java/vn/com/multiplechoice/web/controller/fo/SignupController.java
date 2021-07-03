package vn.com.multiplechoice.web.controller.fo;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.Period;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.UserConverterService;
import vn.com.multiplechoice.business.service.MailService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.business.service.VerificationCodeService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.form.SignUpForm;
import vn.com.multiplechoice.web.utils.RequestUtil;

@Controller
@RequestMapping("/fo")
public class SignupController {
    private static final Logger log = LoggerFactory.getLogger(SignupController.class);

    private static final String SIGNUP_FORM = "signUpForm";
    private static final String SIGNUP = "fo/signup";

    @Autowired
    private UserConverterService userConverterService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute(SIGNUP_FORM, new SignUpForm());

        return SIGNUP;
    }

    @PostMapping("/signup")
    public String createUser(Model model, @Valid SignUpForm signUpRequest, BindingResult bindingResult, HttpServletRequest request) {
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
            User user = userConverterService.toNewEntity(signUpRequest);
            userService.save(user);
            // send verify email here
            model.addAttribute(SIGNUP_FORM, new SignUpForm());
            model.addAttribute("successMessage", "Đăng ký thành công. Xin hãy kiểm tra email để kích hoạt tài khoản!");

            // send verify email
            try {
                mailService.sendVerificationEmail(user, RequestUtil.getSiteURL(request));
            } catch (UnsupportedEncodingException | MessagingException e) {
                log.error(e.getMessage());
            }
        }

        return SIGNUP;
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (verificationCodeService.verifyToken(code)) {
            return "/fo/verify_success";
        } else {
            return "/fo/verify_fail";
        }
    }
    
}
