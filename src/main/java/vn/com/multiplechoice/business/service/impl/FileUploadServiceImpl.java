package vn.com.multiplechoice.business.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.com.multiplechoice.business.config.ApplicationConfig;
import vn.com.multiplechoice.business.service.FileUploadService;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {
    
    private static final Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Autowired
    private ApplicationConfig applicationConfig;
    
    @Override
    public void uploadFile(Long userId, String fileName, MultipartFile multipartFile) {
        log.info("------------------ FileUploadServiceImpl - uploadFile START -----------------------------");
        String uploadDir = applicationConfig.getTemplateUploadPath()+ "/" + userId + "/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.error("Could not save file: {}", fileName);
        }

        log.info("------------------ FileUploadServiceImpl - uploadFile END -----------------------------");
    }

}
