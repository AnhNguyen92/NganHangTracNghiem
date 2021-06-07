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
    ApplicationConfig applicationConfig; 
    @Bean
    public JavaMailSender getJavaMailSender() {
//        activate gmail used by less secure apps
//        https://www.google.com/settings/security/lesssecureapps
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
 
        mailSender.setUsername(applicationConfig.getMailUsername());
        mailSender.setPassword(applicationConfig.getMailPassword());
 
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", applicationConfig.isMailSMTPAuth());
        props.put("mail.smtp.starttls.enable", applicationConfig.isMailSMTPStarttlsEnable());
        props.put("mail.debug", "true");
        
        return mailSender;
    }
    
}
