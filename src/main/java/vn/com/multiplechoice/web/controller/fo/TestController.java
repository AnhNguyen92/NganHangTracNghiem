package vn.com.multiplechoice.web.controller.fo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.dao.model.Options;

@Controller
@RequestMapping("/fo/contests")
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);

	@GetMapping
	public String createTest(Model model) {
		model.addAttribute("options", new Options());
		return "fo/test";
	}
	
	@PostMapping
    public String save(Options options, Model model) {
        model.addAttribute("options", options);
        return "fo/saved";
    }
	
}
