package vn.com.multiplechoice.web.controller.fo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
import vn.com.multiplechoice.business.service.HeaderTemplateService;
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
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);

	private static final String OPTIONS = "options";
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
	private ApplicationConfig appconfig;

	@Autowired
	private HeaderTemplateService headerTemplateService;

	@GetMapping("")
	public String createTest(Model model) {
		User user = onlineUserUtil.getOnlineUser();
		List<Question> questions = questionService.findByAuthor(user.getId());
		Options option = new Options();
		List<String> questIds = questions.stream().map(q -> String.valueOf(q.getId())).collect(Collectors.toList());
		model.addAttribute(OPTIONS, option);
		model.addAttribute("questIds", questIds);

		return "fo/test";
	}

	@GetMapping("/{id}/edit")
	public String editTest(@PathVariable(name = "id") Long id, Model model) {
		Test test = testService.findById(id);
		if (test == null) {
			return "/fo/404";
		}

		Set<Question> questions = test.getQuestions();

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
		Test test = testService.findById(id);
		if (test == null) {
			return "/fo/404";
		}
		model.addAttribute("test", test);
		model.addAttribute("answerLabels", ANSWER_LABELS);
		if (test.getHeader() != null) {
			HeaderTemplate headerTemplate = test.getHeader();
			String sourcePath = appconfig.getTemplateUploadPath() + File.separatorChar
					+ headerTemplate.getSourcePath();
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
	public String saveOrUpdate(Options options, @RequestParam("file") MultipartFile multipartFile, Model model) throws FileNotFoundException {
		User creator = onlineUserUtil.getOnlineUser();
		Test test = new Test();
		if (options.getTestId() != null) {
			test = testService.findById(options.getTestId());
		} else {
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
		Set<Question> selectedQuestions = new HashSet<>(questionService.findAllById(selecteds));
		test.setQuestions(selectedQuestions);

		test.setNumOfQuestions(options.getSelected().size());
		test.setContent(options.getContent());
		test.setPublic(options.isPublic());
		test.setExecuteTime(options.getExecuteTime());

		testService.save(test);
		model.addAttribute(OPTIONS, options);

		updateHeader(multipartFile, creator, test);

		return detail(test.getId(), model);
	}

	private void updateHeader(MultipartFile multipartFile, User creator, Test test) {
		// save/update header
		if (multipartFile != null && !multipartFile.isEmpty()) {
			// remove old header template
			if (test.getHeader() != null) {
				HeaderTemplate oldHeaderTemplate = headerTemplateService.findById(test.getHeader().getId());
				fileStorageService.delete(appconfig.getTemplateUploadPath() + oldHeaderTemplate.getSourcePath());
				try {
					test.setHeader(null);
					headerTemplateService.delete(oldHeaderTemplate);
				} catch (IOException e) {
					logger.error("{}", e.getMessage());
				}
			}
			HeaderTemplate headerTemplate = new HeaderTemplate();
			headerTemplate.setCreateDate(new Date());
			headerTemplate.setUser(creator);
			String originalFilename = multipartFile.getOriginalFilename();
			headerTemplate.setOriginalName(originalFilename);
			headerTemplate.setGeneratedName(
					UUID.randomUUID().toString() + originalFilename.substring(originalFilename.indexOf(".")));
			char separatorchar = File.separatorChar;
			String sourcePath = String.format("%s%s%s%s", separatorchar, test.getId(), separatorchar, headerTemplate.getGeneratedName());
			headerTemplate.setSourcePath(sourcePath);
			boolean uploadSuccessfull = fileStorageService.upload(
					appconfig.getTemplateUploadPath() + separatorchar + test.getId(),
					headerTemplate.getGeneratedName(), multipartFile);
			if (uploadSuccessfull) {
				headerTemplateService.save(headerTemplate);
			}

			test.setHeader(headerTemplate);
			testService.save(test);
		}
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
