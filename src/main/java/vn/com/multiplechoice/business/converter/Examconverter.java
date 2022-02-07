package vn.com.multiplechoice.business.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.web.dto.ExamDto;
// import vn.com.multiplechoice.web.dto.QuestionDto;

@Component
public class Examconverter {
//	public ExamDto convertTestToExam(Test test) {
//		ExamDto examDto = new ExamDto();
//		examDto.setTitle(test.getContent());
//		List<QuestionDto> questionDtos = examDto.getQuestions();
//		for (Question question : test.getQuestions()) {
//			QuestionDto dto = new QuestionDto();
//			dto.setAnswerA(question.getAnswerA());
//			dto.setAnswerB(question.getAnswerB());
//			dto.setAnswerC(question.getAnswerC());
//			dto.setAnswerD(question.getAnswerD());
//			dto.setAnswerE(question.getAnswerE());
//			dto.setAnswerF(question.getAnswerF());
//			dto.setAnswerG(question.getAnswerG());
//			dto.setAnswerH(question.getAnswerH());
//			questionDtos.add(dto);
//		}
//
//		return examDto;
//	}

}
