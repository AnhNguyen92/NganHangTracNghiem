package vn.com.multiplechoice.web.controller.fo;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.criteria.TestCriteria;
import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.model.enums.TestStatus;
import vn.com.multiplechoice.dao.model.paging.Paged;
import vn.com.multiplechoice.dao.model.paging.Paging;
import vn.com.multiplechoice.web.utils.DateUtil;

@Controller
@RequestMapping("/fo/index")
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private TestService testService;

	@GetMapping("")
	public String index(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int size,
			TestCriteria testCriteria, Model model) {
		logger.info("------- Start get test list -------");
		logger.info("{}", testCriteria);
		logger.info("pageNumber = {}", pageNumber);
		logger.info("pageSize = {}", size);

		if (testCriteria.getDateRange() == null) {
			DateRange dateRange = new DateRange();
			dateRange.setFromDate(DateUtil.getFirstDateOfMonth());
			dateRange.setToDate(new Date());
			testCriteria.setDateRange(dateRange);
		}
		testCriteria.setStatus(TestStatus.APPROVED);
		Paged<Test> paged = new Paged<>();
		List<Test> tests = testService.findAll(testCriteria);
		if (testCriteria.getSize() != null) {
			size = testCriteria.getSize().getValue();
		}
		int pageSize = pageNumber * size;
		int start = tests.isEmpty() ? 0 : ((pageNumber - 1) * size + 1);
		int end = (pageSize > tests.size()) ? tests.size() : pageSize;
		if (!tests.isEmpty()) {
			Pageable pageable = PageRequest.of(pageNumber - 1, size);
			Page<Test> testPage = new PageImpl<>(tests.subList((pageNumber - 1) * size, end), pageable,
					tests.size());
			paged = new Paged<>(testPage, Paging.of(testPage.getTotalPages(), pageNumber, size));
		}

		model.addAttribute("tests", paged);
		model.addAttribute("size", size);
		model.addAttribute("_START_", start);
		model.addAttribute("_END_", end);
		model.addAttribute("_TOTAL_", tests.size());

		return "fo/index";
	}

	@PostMapping("")
	public String search(TestCriteria testCriteria, Model model) {
		logger.info("------- Start search form -------");
		int pageNumber = 1;
		int size = 20;

		return index(pageNumber, size, testCriteria, model);
	}
}
