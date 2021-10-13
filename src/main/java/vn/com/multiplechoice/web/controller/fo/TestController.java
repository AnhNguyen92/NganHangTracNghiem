package vn.com.multiplechoice.web.controller.fo;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.model.Options;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

@Controller
@RequestMapping("/fo/contests")
public class TestController {
	Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private QuestionService questionService;

	@Autowired
	OnlineUserUtil onlineUserUtil;

	@GetMapping
	public String createTest(Model model) {
		User user = onlineUserUtil.getOnlineUser();
		List<Question> questions = questionService.findByAuthor(user.getId());
		Options option = new Options();
		List<String> questIds =  questions.stream().map(question -> question.getId().toString()).collect(Collectors.toList());
		model.addAttribute("options", option);
		model.addAttribute("questIds", questIds);
		
		return "fo/test";
	}

	@PostMapping
	public String save(Options options, Model model) {
		model.addAttribute("options", options);
		return "fo/saved";
	}

}
