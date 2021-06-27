package vn.com.multiplechoice.business.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Autowired
    @Bean
    public JavaMailSender getJavaMailSender(ApplicationConfig applicationConfig) {
        // activate gmail used by less secure apps
        // https://www.google.com/settings/security/lesssecureapps
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(applicationConfig.getMailHost());
        mailSender.setPort(applicationConfig.getMailPort());

        mailSender.setUsername(applicationConfig.getMailUsername());
        mailSender.setPassword(applicationConfig.getMailPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", applicationConfig.getMailProtocol());
        props.put("mail.smtp.auth", applicationConfig.isMailSMTPAuth());
        props.put("mail.smtp.starttls.enable", applicationConfig.isMailSMTPStarttlsEnable());
        props.put("mail.debug", true);

        return mailSender;
    }

}
