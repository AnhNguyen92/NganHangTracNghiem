package vn.com.multiplechoice.web.controller.fo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.dao.model.Test;

@Controller
@RequestMapping("/print-file-final")
public class PrintFileFinalController {

    @GetMapping
    public String addHeaderAndFooter(Model model) {
        
        Test test = new Test();
        test.setContent("--- Hết ---");
        model.addAttribute("test", test);
        return "print-file-final";
    }

    @PostMapping
    public String save(Test test, Model model) {
        model.addAttribute("test", test);
        return "saved";
    }
    
}
