package vn.com.multiplechoice.business.converter;

import java.util.ArrayList;
import java.util.List;

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
		exam.setId(test.getId());
		exam.setCreator(test.getCreator());
		exam.setExecuteTime(test.getExecuteTime());
		exam.setCreateDate(test.getCreateDate());
		exam.setHeader(test.getHeader());
		exam.setNumOfQuestions(test.getNumOfQuestions());
		exam.setTitle(test.getContent());
		List<MCQDto> questions = new ArrayList<>();
		for (Question question : test.getQuestions()) {
			MCQDto mcqDto = questionConverter.toDto(question);
			questions.add(mcqDto);
		}
		exam.setQuestions(questions);

		return exam;
	}
}
