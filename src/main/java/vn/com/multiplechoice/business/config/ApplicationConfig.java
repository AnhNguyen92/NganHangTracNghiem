package vn.com.multiplechoice.business.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Value("${template.upload.path}")
    private String templateUploadPath;

    public String getTemplateUploadPath() {
        return templateUploadPath;
    }

}
