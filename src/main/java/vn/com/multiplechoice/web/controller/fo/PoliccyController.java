package vn.com.multiplechoice.web.controller.fo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PoliccyController {
    @GetMapping(value = "/policy")
    public String policy() {
        return "fo/policy";
    }
}
