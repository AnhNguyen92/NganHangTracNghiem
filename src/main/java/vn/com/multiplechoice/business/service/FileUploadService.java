package vn.com.multiplechoice.business.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public void uploadFile(Long userId, String fileName, MultipartFile multipartFile);
}
