package vn.com.multiplechoice.business.service.impl;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;
import vn.com.multiplechoice.business.config.ApplicationConfig;
import vn.com.multiplechoice.business.service.MailService;
import vn.com.multiplechoice.business.service.VerificationCodeService;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.VerificationCode;
import vn.com.multiplechoice.dao.model.enums.VerificationType;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private ApplicationConfig applicationConfig;
    
    @Autowired
    private VerificationCodeService verificationCodeService;
    
    @Override
    public void sendResetPasswordEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(applicationConfig.getMailUsername(), "MQC Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Xin chào,</p>" + "<p>Bạn nhận được email này vì đã gửi yêu cầu đặt lại mật khẩu.</p>" + "<p>Click vào đường dẫn dưới đây để thay đổi mật khẩu của bạn:</p>"
                + "<p><a href=\"" + link + "\">Đổi mật khẩu</a></p>" + "<br>" + "<p>Bỏ qua thư này nếu bạn nhớ mật khẩu, "
                + "hoặc bạn không thực hiện hành động này.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL)  throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = applicationConfig.getMailUsername();
        String senderName = "MQC Support";
        String subject = "Xin hãy xác nhận quá trình đăng ký";
        String content = "Chào [[name]],<br>"
                + "Vui lòng nhấn vào đường link dưới đây để xác thực tài khoản của bạn:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">XÁC THỰC</a></h3>"
                + "Lưu ý: Đường link có giá trị trong 1 ngày"
                + "Cảm ơn,<br>"
                + "MQC.";
         
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
         
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
         
        content = content.replace("[[name]]", user.getLastname() + " " + user.getFirstname());
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setType(VerificationType.REGISTER);
        verificationCode.setExpireTime(LocalDateTime.now().plusDays(1));
        verificationCode.setUser(user);
        String randomtoken = RandomString.make(45);
        verificationCode.setToken(randomtoken);
        verificationCodeService.save(verificationCode);
        String verifyURL = siteURL + "/fo/verify?code=" + randomtoken;
         
        content = content.replace("[[URL]]", verifyURL);
         
        helper.setText(content, true);
         
        mailSender.send(message);
    }

}
