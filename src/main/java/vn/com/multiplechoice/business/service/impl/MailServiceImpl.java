package vn.com.multiplechoice.business.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.config.ApplicationConfig;
import vn.com.multiplechoice.business.service.MailService;

@Service
@Transactional
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private ApplicationConfig applicationConfig;
    
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

}
