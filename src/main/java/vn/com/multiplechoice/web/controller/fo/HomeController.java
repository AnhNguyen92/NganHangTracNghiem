package vn.com.multiplechoice.web.controller.fo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fo")
public class HomeController {
	@Value("${welcome.message}")
	private String message;

	@GetMapping(value = {"/", "index"})
	public String index(Model model) {
		model.addAttribute("message", message);

		return "fo/index";
	}

}
