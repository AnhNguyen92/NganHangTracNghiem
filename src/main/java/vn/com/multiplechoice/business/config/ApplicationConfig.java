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

    @Value("${spring.mail.transport.protocol}")
    private String mailTransportProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean mailSMTPAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean mailSMTPStarttlsEnable;

    @Value("${spring.mail.debug}")
    private boolean mailDebugMode;

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

    public String getMailTransportProtocol() {
        return mailTransportProtocol;
    }

    public boolean isMailSMTPAuth() {
        return mailSMTPAuth;
    }

    public boolean isMailSMTPStarttlsEnable() {
        return mailSMTPStarttlsEnable;
    }

    public boolean isMailDebugMode() {
        return mailDebugMode;
    }

}
