package vn.com.multiplechoice.web.controller.bo;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.criteria.TestCriteria;
import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.Test;

@Controller
@RequestMapping("/bo/tests")
public class TestController {
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Autowired
	private TestService testService;

	@RequestMapping(value = { "", "/list" })
	public String list(TestCriteria testCriteria, Model model) {
		logger.info("------- Start get test list -------");
		if (testCriteria.getDateRange() == null) {
		    DateRange dateRange = new DateRange();
		    dateRange.setFromDate(new Date());
		    dateRange.setToDate(new Date());
            testCriteria.setDateRange(dateRange);
		}
		List<Test> tests = testService.findAll(testCriteria);
		model.addAttribute("tests", tests);
		
		return "bo/tests";
	}

	@GetMapping("/{id}")
	public String detail(Model model, @PathVariable long id) {
		logger.info("------- Start get test by id = {} -------", id);
		Test test = testService.findOne(id);
		if (test == null) {
			return "bo/errors/404";
		}
		model.addAttribute("test", test);

		return "bo/test";
	}

	public String create(Model model) {
		logger.info("------- Start create a new test -------");
		model.addAttribute("test", new Test());
		return "bo/test";
	}

	public String save(Test test) {
		logger.info("------- Start save new test -------");
		testService.save(test);

		return "redirect:bo/tests/" + test.getId();
	}

}
