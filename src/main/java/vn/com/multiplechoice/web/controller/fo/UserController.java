package vn.com.multiplechoice.web.controller.fo;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.multiplechoice.business.converter.UserConverter;
import vn.com.multiplechoice.business.service.MailService;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.business.service.VerificationCodeService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.VerificationCode;
import vn.com.multiplechoice.dao.model.enums.VerificationType;
import vn.com.multiplechoice.dao.model.paging.Paged;
import vn.com.multiplechoice.dao.model.paging.Paging;
import vn.com.multiplechoice.web.dto.UserDto;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;
import vn.com.multiplechoice.web.utils.RequestUtil;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String ERROR = "error";
    private static final String MESSAGE = "message";
    private static final String FO_FORGOT_PASSWORD = "fo/forgot-password";
    private static final String FO_RESET_PASSWORD = "fo/reset-password";

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private MailService mailservice;

    @Autowired
    private OnlineUserUtil onlineUserUtil;
    
    @Autowired
    private QuestionService questionService;
    
    @GetMapping("/fo/user/profile")
    public String profile(Model model) {
        User user = onlineUserUtil.getOnlineUser();
        model.addAttribute("user", userConverter.toDto(user));

        return "fo/user-profile";
    }

    @PostMapping("/fo/user/save")
    public String updateProfile(Model model, UserDto userDto) {
        User user = onlineUserUtil.getOnlineUser();
        userConverter.updateUser(user, userDto);
        userService.save(user);
        if (userDto.getPassword() != null && userDto.getPassword().length() >= 6) {
            SecurityContextHolder.clearContext();
            return "fo/login";
        }
        model.addAttribute("user", userConverter.toDto(user));

        return "fo/index";
    }

    @GetMapping("/fo/forgot-password")
    public String forgotPassword() {
        return FO_FORGOT_PASSWORD;
    }

    @PostMapping("/fo/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        User user = userService.findByEmail(email);
        if (user != null) {
            VerificationCode code = new VerificationCode();
            code.setToken(RandomString.make(45));
            code.setType(VerificationType.FORGOT_PASSWORD);
            code.setExpireTime(LocalDateTime.now().plusDays(1));
            code.setUser(user);
            verificationCodeService.save(code);
            try {
                mailservice.sendResetPasswordEmail(email, RequestUtil.getSiteURL(request) + "/fo/reset-password?token=" + code.getToken());
                model.addAttribute(MESSAGE, "Email đã được gửi. Vui lòng kiểm tra hòm thư của bạn!");
            } catch (UnsupportedEncodingException | MessagingException e) {
                log.error(e.getMessage());
                model.addAttribute("errr", "Send mail fail!");
            }
        } else {
            model.addAttribute(ERROR, "Email không tồn tại!");
            model.addAttribute("email", email);
        }

        return FO_FORGOT_PASSWORD;
    }

    @GetMapping("/fo/reset-password")
    public String resetPassword(@Param(value = "token") String token, Model model) {
        VerificationCode verificationCode = verificationCodeService.findByToken(token);
        if (verificationCode != null && verificationCode.getExpireTime().isAfter(LocalDateTime.now())) {
            model.addAttribute("token", token);
        } else {
            model.addAttribute(ERROR, "Token không hợp lệ!");
        }

        return FO_RESET_PASSWORD;
    }

    @PostMapping("/fo/reset-password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        VerificationCode verificationCode = verificationCodeService.findByToken(token);
        if (verificationCode == null) {
            model.addAttribute(ERROR, "Token không đúng");
        } else {
            String password = request.getParameter("password");
            if (password == null || password.length() < 6) {
                model.addAttribute(ERROR, "Mật khẩu phải từ 6 ký tự trở lên");
            } else {
                User user = verificationCode.getUser();
                userService.resetNewPassword(user, password);
                model.addAttribute(MESSAGE, "Cập nhật mật khẩu thành công!");
            }
        }

        return FO_RESET_PASSWORD;
    }

    @GetMapping("/fo/user/questions")
    public String listQuesstion(Model model, @RequestParam(defaultValue = "1") int pageNumber) {
        User user = onlineUserUtil.getOnlineUser();
        List<Question> questions = questionService.findByAuthor(user.getId());
        int size = 10;
        int pageSize = pageNumber * size;
        int start = (!questions.isEmpty() ) ? ( (pageNumber - 1) * size ) : 0;
        int end = (pageSize > questions.size()) ? questions.size() : pageSize;
        Paged<Question> paged = new Paged<>();
        if (!questions.isEmpty()) {
			Pageable pageable = PageRequest.of(pageNumber - 1, size);
			Page<Question> questionPage = new PageImpl<>(questions.subList((pageNumber - 1) * size, end), pageable,
					questions.size());
			paged = new Paged<>(questionPage, Paging.of(questionPage.getTotalPages(), pageNumber, size));
		}
        
        model.addAttribute("questions", paged);
		model.addAttribute("size", size);
		model.addAttribute("_START_", start);
		model.addAttribute("_END_", end);
		model.addAttribute("_TOTAL_", questions.size());
        
        return "fo/user-question-list";
    }
}
