package vn.com.multiplechoice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("print-file-final")
public class PrintFileFinalController {

    @GetMapping
    public String addHeaderAndFooter(Model model) {
        return "print-file-final";
    }

}
