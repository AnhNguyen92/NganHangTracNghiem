package vn.com.multiplechoice.business.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Value("${template.upload.path}")
    private String templateUploadPath;

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String mailProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean mailSMTPAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean mailSMTPStarttlsEnable;

    public String getTemplateUploadPath() {
        return templateUploadPath;
    }

    public String getMailHost() {
        return mailHost;
    }

    public int getMailPort() {
        return mailPort;
    }

    public String getMailUsername() {
        return mailUsername;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getMailProtocol() {
        return mailProtocol;
    }

    public boolean isMailSMTPAuth() {
        return mailSMTPAuth;
    }

    public boolean isMailSMTPStarttlsEnable() {
        return mailSMTPStarttlsEnable;
    }

}
