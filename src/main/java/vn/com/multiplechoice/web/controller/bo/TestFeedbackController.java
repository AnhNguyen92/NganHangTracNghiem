package vn.com.multiplechoice.web.controller.bo;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.multiplechoice.business.service.TestFeedbackService;
import vn.com.multiplechoice.dao.criteria.TestFeedbackCriteria;
import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.TestFeedback;
import vn.com.multiplechoice.dao.model.paging.Paged;
import vn.com.multiplechoice.dao.model.paging.Paging;

@Controller
@RequestMapping("/bo/test-feedbacks")
public class TestFeedbackController {
	private static final Logger logger = LoggerFactory.getLogger(TestFeedbackController.class);

	@Autowired
	private TestFeedbackService feedbackService;

	@GetMapping("")
	public String list(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int size,
			TestFeedbackCriteria testFeedbackCriteria, Model model) {
		logger.info("------- Start get TestFeedback list -------");
		logger.info("{}", testFeedbackCriteria);
		logger.info("pageNumber = {}", pageNumber);
		logger.info("pageSize = {}", size);

		if (testFeedbackCriteria.getDateRange() == null) {
			DateRange dateRange = new DateRange();
			dateRange.setFromDate(new Date());
			dateRange.setToDate(new Date());
			testFeedbackCriteria.setDateRange(dateRange);
		}

		Paged<TestFeedback> paged = new Paged<>();
		List<TestFeedback> testFeedbacks = feedbackService.findAll(testFeedbackCriteria);
		if (testFeedbackCriteria.getSize() != null) {
			size = testFeedbackCriteria.getSize().getValue();
		}
		int pageSize = pageNumber * size;
		int start = testFeedbacks.isEmpty() ? 0 : ((pageNumber - 1) * size + 1);
		int end = (pageSize > testFeedbacks.size()) ? testFeedbacks.size() : pageSize;
		if (!testFeedbacks.isEmpty()) {
			Pageable pageable = PageRequest.of(pageNumber - 1, size);
			Page<TestFeedback> testFeedbackPage = new PageImpl<>(testFeedbacks.subList((pageNumber - 1) * size, end), pageable,
					testFeedbacks.size());
			paged = new Paged<>(testFeedbackPage, Paging.of(testFeedbackPage.getTotalPages(), pageNumber, size));
		}

		model.addAttribute("testFeedbacks", paged);
		model.addAttribute("size", size);
		model.addAttribute("_START_", start);
		model.addAttribute("_END_", end);
		model.addAttribute("_TOTAL_", testFeedbacks.size());

		return "bo/test-feedbacks";
	}

	@PostMapping("")
	public String search(TestFeedbackCriteria testFeedbackCriteria, Model model) {
		logger.info("------- Start search form for testFeedback -------");
		int pageNumber = 1;
		int size = testFeedbackCriteria.getSize().getValue();

		return list(pageNumber, size, testFeedbackCriteria, model);
	}

	@GetMapping("/{id}")
	public String update(Model model, @PathVariable long id) {
		logger.info("------- Start update testFeedback detail by id = {} -------", id);
		TestFeedback testFeedback = feedbackService.findOne(id);
		if (testFeedback == null) {
			return "bo/errors/404";
		}

		return "redirect:/bo/test-feedbacks";
	}

}
