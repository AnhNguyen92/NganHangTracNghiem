package vn.com.multiplechoice.web.controller.fo;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.model.Test;

@Controller
@RequestMapping(value = "/fo/do-exam")
public class ExamController {
	private static final Logger log = LoggerFactory.getLogger(ExamController.class);

	@Autowired
	TestService testService;

	@GetMapping("/{id}")
	public String doExam(Model model, @PathVariable("id") Long id) throws ParseException {
		log.info("===  Start do exam with test has id = {}  ===", id);
		Test test = testService.findOne(id);
		String executeTime = test.getExecuteTime();
		
		model.addAttribute("executeTime", executeTime);
		model.addAttribute("test", test);
		return "/fo/do-exam";
	}
}
