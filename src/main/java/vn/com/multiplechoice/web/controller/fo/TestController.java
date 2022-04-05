package vn.com.multiplechoice.web.controller.fo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

import vn.com.multiplechoice.business.config.ApplicationConfig;
import vn.com.multiplechoice.business.service.FileStorageService;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.model.HeaderTemplate;
import vn.com.multiplechoice.dao.model.Options;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.TestStatus;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

@Controller
@RequestMapping("/fo/contests")
public class TestController {
	private static final String OPTIONS = "options";
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	private static final String[] ANSWER_LABELS = new String[] { "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D",
			"Đáp án E", "Đáp án F", "Đáp án G", "Đáp án H" };

	@Autowired
	private QuestionService questionService;

	@Autowired
	private OnlineUserUtil onlineUserUtil;

	@Autowired
	private TestService testService;

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	private ApplicationConfig applicationConfig;

	@GetMapping("")
	public String createTest(Model model) {
		User user = onlineUserUtil.getOnlineUser();
		List<Question> questions = questionService.findByAuthor(user.getId());
		Options option = new Options();
		List<String> questIds = questions.stream().map(question -> question.getId().toString())
				.collect(Collectors.toList());
		model.addAttribute(OPTIONS, option);
		model.addAttribute("questIds", questIds);

		return "fo/test";
	}

	@GetMapping("/{id}/edit")
	public String editTest(@PathVariable(name = "id") Long id, Model model) {
		Test test = testService.findOne(id);
		if (test == null) {
			return "/fo/404";
		}

		List<Question> questions = test.getQuestions();

		List<Long> questIds = questions.stream().map(Question::getId).collect(Collectors.toList());
		Options option = new Options();
		option.setTestId(test.getId());
		option.setContent(test.getContent());
		option.setExecuteTime(test.getExecuteTime());
		option.setPublic(test.isPublic());
		option.setSelected(questIds);
		model.addAttribute(OPTIONS, option);
		model.addAttribute("addedQuestIds", questIds);

		List<Question> availableQuestions = questionService.findByAuthor(onlineUserUtil.getOnlineUserID());
		List<Long> availableQuestIds = availableQuestions.stream().map(Question::getId).collect(Collectors.toList());
		availableQuestIds.removeAll(questIds);
		model.addAttribute("questIds", availableQuestIds);

		return "fo/test";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable(name = "id") Long id, Model model) throws FileNotFoundException {
		Test test = testService.findOne(id);
		if (test == null) {
			return "/fo/404";
		}
		model.addAttribute("test", test);
		model.addAttribute("answerLabels", ANSWER_LABELS);
		if (test.getHeader() != null) {
			HeaderTemplate headerTemplate = test.getHeader();
			String sourcePath = applicationConfig.getTemplateUploadPath() + headerTemplate.getSourcePath();
			FileInputStream templateFile = new FileInputStream(sourcePath);
			String header = parseHeaderTemplateToHtml(templateFile);
			model.addAttribute("header", header);
		}

		return "/fo/test-detail";
	}

	@GetMapping("/user")
	public String userTestList(Model model) {
		User user = onlineUserUtil.getOnlineUser();
		List<Test> tests = testService.findByUserId(user.getId());

		Pageable pageable = PageRequest.of(0, 10);
		Page<Test> page = new PageImpl<>(tests, pageable, tests.size());

		model.addAttribute("page", page);
		model.addAttribute("size", page.getSize());

		return "/fo/user-test-list";
	}

	@PostMapping
	public String saveOrUpdate(Options options, @RequestParam("file") MultipartFile multipartFile, Model model) {
		Test test = new Test();
		if (options.getTestId() != null) {
			test = testService.findOne(options.getTestId());
		} else {
			User creator = onlineUserUtil.getOnlineUser();
			test.setCreator(creator);
			test.setCreateDate(new Date());
			if (creator.getRole().equals(UserRole.USER)) {
				test.setStatus(TestStatus.WAITING);
			} else {
				test.setStatus(TestStatus.APPROVED);
				test.setInspector(creator);
			}
		}

		List<Long> selecteds = options.getSelected();
		List<Long> existedQuestion = test.getQuestions().stream().map(Question::getId).collect(Collectors.toList());
		selecteds.removeAll(existedQuestion);
		List<Question> questions = questionService.findAllById(selecteds);
		

		// save header
		if (multipartFile != null && !multipartFile.isEmpty()) {
			fileStorageService.upload(applicationConfig.getTemplateUploadPath(), multipartFile.getOriginalFilename(),
					multipartFile);
		}

		test.setNumOfQuestions(options.getSelected().size());
		test.setContent(options.getContent());
		test.getQuestions().addAll(questions);
		test.setPublic(options.isPublic());
		test.setExecuteTime(options.getExecuteTime());

		testService.save(test);

		model.addAttribute(OPTIONS, options);
		return "fo/saved";
	}

	private String parseHeaderTemplateToHtml(InputStream inputStream) {
		// header docx
		DocumentConverter converter = new DocumentConverter();
		String html = "";
		try {
			Result<String> result = converter.convertToHtml(inputStream);
			html = result.getValue(); // The generated HTML
			// logger.info("html:\n {}", html);
			// Set<String> warnings = result.getWarnings();
			// logger.info("warnings: \n{}", warnings);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		return html;
	}

}
