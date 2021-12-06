package vn.com.multiplechoice.web.controller.fo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/exams")
public class ExamController {

private static final Logger log = LoggerFactory.getLogger(ExamController.class);

@GetMapping("/{testId}")
public String doExam(@PathVariable Long testId) {
    log.info("===  Start do exam with test has id = {}  ===", testId);

    return "";
}
}
