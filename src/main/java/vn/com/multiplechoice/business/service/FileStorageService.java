package vn.com.multiplechoice.business.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public boolean upload(String uploadDir, String fileName, MultipartFile multipartFile);

    public boolean delete(String fileName);
}
