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

import vn.com.multiplechoice.business.service.UserRequestService;
import vn.com.multiplechoice.dao.criteria.UserRequestCriteria;
import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.UserRequest;
import vn.com.multiplechoice.dao.model.paging.Paged;
import vn.com.multiplechoice.dao.model.paging.Paging;

@Controller
@RequestMapping("/bo/comments")
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	@Autowired
	private UserRequestService userRequestService;

	@GetMapping("")
	public String list(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int size,
			UserRequestCriteria userRequestCriteria, Model model) {
		logger.info("------- Start get userRequest list -------");
		logger.info("{}", userRequestCriteria);
		logger.info("pageNumber = {}", pageNumber);
		logger.info("pageSize = {}", size);

		if (userRequestCriteria.getDateRange() == null) {
			DateRange dateRange = new DateRange();
			dateRange.setFromDate(new Date());
			dateRange.setToDate(new Date());
			userRequestCriteria.setDateRange(dateRange);
		}

		Paged<UserRequest> paged = new Paged<>();
		List<UserRequest> userRequests = userRequestService.findAll(userRequestCriteria);
		if (userRequestCriteria.getSize() != null) {
			size = userRequestCriteria.getSize().getValue();
		}
		int pageSize = pageNumber * size;
		int start = userRequests.isEmpty() ? 0 : ((pageNumber - 1) * size + 1);
		int end = (pageSize > userRequests.size()) ? userRequests.size() : pageSize;
		if (!userRequests.isEmpty()) {
			Pageable pageable = PageRequest.of(pageNumber - 1, size);
			Page<UserRequest> userRequestPage = new PageImpl<>(userRequests.subList((pageNumber - 1) * size, end), pageable,
					userRequests.size());
			paged = new Paged<>(userRequestPage, Paging.of(userRequestPage.getTotalPages(), pageNumber, size));
		}

		model.addAttribute("userRequests", paged);
		model.addAttribute("size", size);
		model.addAttribute("_START_", start);
		model.addAttribute("_END_", end);
		model.addAttribute("_TOTAL_", userRequests.size());

		return "bo/user-requests";
	}

	@PostMapping("")
	public String search(UserRequestCriteria userRequestCriteria, Model model) {
		logger.info("------- Start search form for UserRequest -------");
		int pageNumber = 1;
		int size = userRequestCriteria.getSize().getValue();

		return list(pageNumber, size, userRequestCriteria, model);
	}

	@GetMapping("/{id}")
	public String detail(Model model, @PathVariable long id) {
		logger.info("------- Start get userRequest detail by id = {} -------", id);
		UserRequest userRequest = userRequestService.findOne(id);
		if (userRequest == null) {
			return "bo/errors/404";
		}
		model.addAttribute("userRequest", userRequest);

		return "bo/userRequest";
	}
}
