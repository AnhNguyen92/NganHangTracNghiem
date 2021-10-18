package vn.com.multiplechoice.business.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.com.multiplechoice.business.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private static final Logger logger = LoggerFactory.getLogger(FileStorageServiceImpl.class);

    @Override
    public boolean upload(String uploadDir, String fileName, MultipartFile multipartFile) {
        boolean uploadSuccess = false;
        
        Path uploadPath = Paths.get(uploadDir);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            uploadSuccess = true;
        } catch (IOException ex) {
            logger.error("Could not save image file: {}", fileName);
            logger.error("{}", ex.getMessage());
        }
        logger.info("FileStorageService - uploadFile {}", uploadSuccess ? " successful!" : " failed!");
        
        return uploadSuccess;
    }

    /**
     * Delete file and its folder, set fileFolderDirectory = null in case file doesn't separate folder
     */
    public void deleteFileAndFolder(String fileFolderDirectory, String fileDirectory) {
        try {
            Path filePath = Paths.get(fileDirectory);
            Files.delete(filePath);
            if (fileFolderDirectory != null) {
                Path fileFolderPath = Paths.get(fileFolderDirectory);
                Files.delete(fileFolderPath);
            }
            logger.info("------------ DELETE user avatar successfull! ------------");
        } catch (IOException e) {
            logger.info("{}", e.getMessage());
        }
    }
    
    @Override
    public boolean delete(String filename) {
        boolean result = false;
        try {
            result = Files.deleteIfExists(Paths.get(filename));
            logger.info("Delete the file {} {}", filename, result ? " successful!" : " failed!");
        } catch (IOException e) {
            logger.error("{}", e.getMessage());
        }

        return result;
    }

}
