package vn.com.multiplechoice.business.converter;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.model.QuestionAnswerDto;

@Component
public class QuestionConverter {
	private static final Logger logger = LoggerFactory.getLogger(QuestionConverter.class);

	private static final String[] ANSWER_LABELS = new String[] { "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D",
			"Đáp án E", "Đáp án F", "Đáp án G", "Đáp án H" };

	public Question toEntity(MCQDto dto) {
		// not implement yet
		return null;
	}

	public MCQDto toDto(Question question) {
		MCQDto dto = new MCQDto();
		dto.setUser(question.getUser());
		dto.setAnswerSuggestion(question.getSuggest());
		dto.setContent(question.getContent());
		QuestionType questionType = question.getQuestionType();
		dto.setType(questionType);
		if (QuestionType.ONE_ANSWER.equals(questionType)) {
			dto.setQuestionAnswerDtos(saveQuestionAnswer(question));
		}

		return dto;
	}

	private List<QuestionAnswerDto> saveQuestionAnswer(Question question) {
		List<QuestionAnswerDto> questionAnswerDtos = new ArrayList<>();
		QuestionAnswerDto answerDto = new QuestionAnswerDto();

		// map answer A
		answerDto.setAnswerContent(question.getAnswerA());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("A"));
		questionAnswerDtos.add(answerDto);

		// map answer B
		answerDto = new QuestionAnswerDto();
		answerDto.setAnswerContent(question.getAnswerB());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("B"));
		questionAnswerDtos.add(answerDto);

		// map answer C
		answerDto = new QuestionAnswerDto();
		answerDto.setAnswerContent(question.getAnswerC());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("C"));
		questionAnswerDtos.add(answerDto);

		// map answer D
		answerDto = new QuestionAnswerDto();
		answerDto.setAnswerContent(question.getAnswerD());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("D"));
		questionAnswerDtos.add(answerDto);

		// map answer E
		if (question.getAnswerE() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerE());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("E"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer F
		if (question.getAnswerF() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerF());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("F"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer G
		if (question.getAnswerG() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerG());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("G"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer H
		if (question.getAnswerH() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerH());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("H"));
			questionAnswerDtos.add(answerDto);
		}

		return randomAnswerPosition(questionAnswerDtos);
	}

	private List<QuestionAnswerDto> randomAnswerPosition(List<QuestionAnswerDto> questionAnswerDtos) {
		List<QuestionAnswerDto> result = new ArrayList<>();
		Random rand;
		try {
			rand = SecureRandom.getInstanceStrong();
			List<Integer> givenList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
			int size = questionAnswerDtos.size();
			givenList = givenList.subList(0, size);

			for (int i = 0; i < size; i++) {
				int randomIndex = rand.nextInt(givenList.size());
				QuestionAnswerDto answerDto = questionAnswerDtos.get(givenList.get(randomIndex));
				givenList.remove(randomIndex);
				answerDto.setAnswerLabel(ANSWER_LABELS[i]);
				answerDto.setOrder(i);
				result.add(answerDto);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}

		return result;
	}
}
