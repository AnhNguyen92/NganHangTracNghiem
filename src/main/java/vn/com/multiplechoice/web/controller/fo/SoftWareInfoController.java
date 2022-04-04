package vn.com.multiplechoice.web.controller.fo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fo/software-info")
public class SoftWareInfoController {
    private static final Logger logger = LoggerFactory.getLogger(SoftWareInfoController.class);
    
    @GetMapping
    public String info(Model model) {
        logger.info("START get software-info");
        
        return "fo/software-info";
    }

}
