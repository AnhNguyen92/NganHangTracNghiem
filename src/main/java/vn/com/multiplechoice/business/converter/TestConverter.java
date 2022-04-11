package vn.com.multiplechoice.business.converter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.web.dto.ExamDto;
import vn.com.multiplechoice.web.model.MCQDto;

@Component
public class TestConverter {
	@Autowired	private QuestionConverter questionConverter;

	public ExamDto toExam(Test test) {
		ExamDto exam = new ExamDto();
		exam.setCreator(test.getCreator());
		exam.setExecuteTime(test.getExecuteTime());
		exam.setHeader(test.getHeader());
		exam.setNumOfQuestions(test.getNumOfQuestions());
		exam.setTitle(test.getContent());
		Set<MCQDto> questions = new HashSet<>();
		for (Question question : test.getQuestions()) {
			MCQDto mcqDto = questionConverter.toDto(question);
			questions.add(mcqDto);
		}
		exam.setQuestions(questions);

		return exam;
	}
}
