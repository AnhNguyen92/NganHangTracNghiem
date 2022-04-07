package vn.com.multiplechoice.web.controller.fo;

import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.multiplechoice.business.service.FileUploadService;
import vn.com.multiplechoice.business.service.HeaderTemplateService;
import vn.com.multiplechoice.business.service.UserService;
import vn.com.multiplechoice.dao.model.HeaderTemplate;
import vn.com.multiplechoice.dao.model.User;

@Controller
@RequestMapping("/fo/file-upload")
public class FileUploadController {
    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private HeaderTemplateService headerTemplateService;
    
    @Autowired
    private FileUploadService fileUploadService;
    
    @GetMapping
    public String main() {
        return "header-template-upload";
    }

    @PostMapping
    public String upload(@RequestParam("fileName") String fileName, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        log.info("-- start to add header template --");
        
        if (!file.isEmpty()) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentPrincipalName = authentication.getName();
            User user = userService.findByUsername(currentPrincipalName);
            // save file
            String originalFilename = file.getOriginalFilename();
            String fileExt = FilenameUtils.getExtension(originalFilename);
            UUID uuid = UUID.randomUUID();
            String uuidfileName = uuid.toString() + "." + fileExt;
            fileUploadService.uploadFile(user.getId(), uuidfileName, file);
            
            // save template
            HeaderTemplate template = new HeaderTemplate();
            template.setName(fileName);
            template.setUser(user);
            template.setSourcePath("/" + user.getId() + "/" +uuidfileName);
            headerTemplateService.save(template);
            redirectAttributes.addFlashAttribute("message", "Bạn đã tải tệp tin " + file.getOriginalFilename() + " thành công!");
        }
        
        return "redirect:fo/file-upload";
    }
    
}
