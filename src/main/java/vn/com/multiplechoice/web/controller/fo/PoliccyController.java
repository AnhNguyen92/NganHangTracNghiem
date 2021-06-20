package vn.com.multiplechoice.web.controller.fo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fo/policy")
public class PoliccyController {
    @GetMapping
    public String policy() {
        return "fo/policy";
    }
}
