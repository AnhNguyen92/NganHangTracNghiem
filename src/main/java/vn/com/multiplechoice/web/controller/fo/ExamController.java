package vn.com.multiplechoice.web.controller.fo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.converter.TestConverter;
import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.web.dto.ExamDto;

@Controller
@RequestMapping(value = "/fo/do-exam")
public class ExamController {
	private static final Logger log = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	TestService testService;

	@Autowired
	TestConverter testConverter;
	
	@GetMapping("/{id}")
	public String exam(Model model, @PathVariable("id") Long id) {
		log.info("===  Start do exam with test has id = {}  ===", id);
		Test test = testService.findById(id);
		String executeTime = test.getExecuteTime();
		
		model.addAttribute("executeTime", executeTime);
		model.addAttribute("examDto", testConverter.toExam(test));
		// model.addAttribute("test", test);
		return "/fo/do-exam";
	}
	
	@PostMapping("")
	public String doExam(Model model, ExamDto examDto) {
		System.out.println(examDto);
		return "redirect:/fo/index";
	}

}
