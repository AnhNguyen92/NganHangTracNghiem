package vn.com.multiplechoice.business.converter;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.QuestionType;
import vn.com.multiplechoice.web.model.MCQDto;
import vn.com.multiplechoice.web.model.QuestionAnswerDto;
import vn.com.multiplechoice.web.utils.OnlineUserUtil;

@Component
public class QuestionConverter {
	private static final Logger logger = LoggerFactory.getLogger(QuestionConverter.class);

	private static final String[] ANSWER_LABELS = new String[] { "Đáp án A", "Đáp án B", "Đáp án C", "Đáp án D",
			"Đáp án E", "Đáp án F", "Đáp án G", "Đáp án H" };
    private static final String[] answerLabelList = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };

    @Autowired
    private OnlineUserUtil onlineUserUtil;
    
	public Question toEntity(MCQDto mcqDto) {
		Question entity = new Question();

		User user = onlineUserUtil.getOnlineUser();
		entity.setUser(user);
		if (QuestionType.ONE_ANSWER.equals(mcqDto.getType())) {
			entity = mapOneAnswerQuestion(mcqDto);
		} else if (QuestionType.MULTIPLE_ANSWER.equals(mcqDto.getType())) {			
			entity.setContent(mcqDto.getContent());
			entity.setSuggest(mcqDto.getAnswerSuggestion());
			entity.setQuestionType(mcqDto.getType());
			String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
					.map(answer -> answerLabelList[answer.getOrder()]).collect(Collectors.joining(","));
			entity.setAnswerPemutation(permutationPosLst);
			entity.setAnswerA(mcqDto.getQuestionAnswerDtos().get(0).getAnswerContent());
			entity.setAnswerB(mcqDto.getQuestionAnswerDtos().get(1).getAnswerContent());
			entity.setAnswerC(mcqDto.getQuestionAnswerDtos().get(2).getAnswerContent());
			entity.setAnswerD(mcqDto.getQuestionAnswerDtos().get(3).getAnswerContent());
		} else if (QuestionType.YES_NO.equals(mcqDto.getType())) {
			entity.setContent(mcqDto.getContent());
			entity.setSuggest(mcqDto.getAnswerSuggestion());
			entity.setQuestionType(QuestionType.YES_NO);
	        String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
					.map(answer -> answerLabelList[answer.getOrder()]).collect(Collectors.joining(","));
			entity.setAnswerPemutation(permutationPosLst);
	        List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
	        entity.setAnswerA(questionAnswerDtos.get(0).getAnswerContent());
	        entity.setAnswerB(questionAnswerDtos.get(1).getAnswerContent());
	        Optional<QuestionAnswerDto> trueAnsPosOptinal = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::getTrueAnswer).findFirst();
	        int trueAnswerPos = trueAnsPosOptinal.isPresent() ? trueAnsPosOptinal.get().getOrder() : 0;
	        entity.setRightAnswer(answerLabelList[trueAnswerPos]);
	        
		} else if (QuestionType.TRUE_FALSE.equals(mcqDto.getType())) {
			entity.setContent(mcqDto.getContent());
			entity.setSuggest(mcqDto.getAnswerSuggestion());
			entity.setQuestionType(QuestionType.TRUE_FALSE);
	        String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
					.map(answer -> answerLabelList[answer.getOrder()]).collect(Collectors.joining(","));
			entity.setAnswerPemutation(permutationPosLst);
		}

        return entity;
	}

	public MCQDto toDto(Question question) {
		MCQDto dto = new MCQDto();
		dto.setUser(question.getUser());
		dto.setAnswerSuggestion(question.getSuggest());
		dto.setContent(question.getContent());
		dto.setAnswerSuggestion(question.getSuggest());
		QuestionType questionType = question.getQuestionType();
		dto.setType(questionType);
		if (QuestionType.ONE_ANSWER.equals(questionType)) {
			dto.setQuestionAnswerDtos(saveQuestionAnswer(question));
		}

		return dto;
	}

	private Question mapOneAnswerQuestion(MCQDto mcqDto) {
		Question question = new Question();
		
		question.setContent(mcqDto.getContent());
        question.setSuggest(mcqDto.getAnswerSuggestion());
        question.setQuestionType(QuestionType.ONE_ANSWER);
        saveQuestionAnswer(question, mcqDto.getQuestionAnswerDtos());
        
        Optional<QuestionAnswerDto> trueAnsPosOptinal = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::getTrueAnswer).findFirst();
        int trueAnswerPos = trueAnsPosOptinal.isPresent() ? trueAnsPosOptinal.get().getOrder() : 0;
        question.setRightAnswer(answerLabelList[trueAnswerPos]);
        String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
                .map(answer -> answerLabelList[answer.getOrder()]).collect(Collectors.joining(","));
        logger.info(permutationPosLst);
        question.setAnswerPemutation(permutationPosLst);
        
        return question;
	}

	private void saveQuestionAnswer(Question question, List<QuestionAnswerDto> questionAnswerDtos) {
        if (!questionAnswerDtos.isEmpty()) {
            question.setAnswerA(questionAnswerDtos.get(0).getAnswerContent());

            if (questionAnswerDtos.size() > 1) {
                question.setAnswerB(questionAnswerDtos.get(1).getAnswerContent());
            }
            if (questionAnswerDtos.size() > 2) {
                question.setAnswerC(questionAnswerDtos.get(2).getAnswerContent());
            }
            if (questionAnswerDtos.size() > 3) {
                question.setAnswerD(questionAnswerDtos.get(3).getAnswerContent());
            }
            if (questionAnswerDtos.size() > 4) {
                question.setAnswerE(questionAnswerDtos.get(4).getAnswerContent());
            }
            if (questionAnswerDtos.size() > 5) {
                question.setAnswerF(questionAnswerDtos.get(5).getAnswerContent());
            }
            if (questionAnswerDtos.size() > 6) {
                question.setAnswerG(questionAnswerDtos.get(6).getAnswerContent());
            }
            if (questionAnswerDtos.size() > 7) {
                question.setAnswerH(questionAnswerDtos.get(7).getAnswerContent());
            }
        }
    }

private List<QuestionAnswerDto> saveQuestionAnswer(Question question) {
		List<QuestionAnswerDto> questionAnswerDtos = new ArrayList<>();
		QuestionAnswerDto answerDto = new QuestionAnswerDto();

		// map answer A
		answerDto.setAnswerContent(question.getAnswerA());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("A"));
		answerDto.setRandomPosition(question.getAnswerPemutation().contains("A"));
		questionAnswerDtos.add(answerDto);

		// map answer B
		answerDto = new QuestionAnswerDto();
		answerDto.setAnswerContent(question.getAnswerB());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("B"));
		answerDto.setRandomPosition(question.getAnswerPemutation().contains("B"));
		questionAnswerDtos.add(answerDto);

		// map answer C
		answerDto = new QuestionAnswerDto();
		answerDto.setAnswerContent(question.getAnswerC());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("C"));
		answerDto.setRandomPosition(question.getAnswerPemutation().contains("C"));
		questionAnswerDtos.add(answerDto);

		// map answer D
		answerDto = new QuestionAnswerDto();
		answerDto.setAnswerContent(question.getAnswerD());
		answerDto.setTrueAnswer(question.getRightAnswer().equals("D"));
		answerDto.setRandomPosition(question.getAnswerPemutation().contains("D"));
		questionAnswerDtos.add(answerDto);

		// map answer E
		if (question.getAnswerE() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerE());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("E"));
			answerDto.setRandomPosition(question.getAnswerPemutation().contains("E"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer F
		if (question.getAnswerF() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerF());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("F"));
			answerDto.setRandomPosition(question.getAnswerPemutation().contains("F"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer G
		if (question.getAnswerG() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerG());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("G"));
			answerDto.setRandomPosition(question.getAnswerPemutation().contains("G"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer H
		if (question.getAnswerH() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setAnswerContent(question.getAnswerH());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("H"));
			answerDto.setRandomPosition(question.getAnswerPemutation().contains("H"));
			questionAnswerDtos.add(answerDto);
		}

		return randomAnswerPosition(questionAnswerDtos);
	}

	private List<QuestionAnswerDto> randomAnswerPosition(List<QuestionAnswerDto> questionAnswerDtos) {
		int size = questionAnswerDtos.size();
		List<QuestionAnswerDto> result = new ArrayList<>(size);

		Random rand;
		try {
			rand = SecureRandom.getInstanceStrong();
			List<Integer> givenList = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				if (questionAnswerDtos.get(i).isRandomPosition()) {
					givenList.add(i);
				}
			}

			for (int i = 0; i < size; i++) {
				QuestionAnswerDto answerDto = questionAnswerDtos.get(i);
				if (answerDto.isRandomPosition()) {
					int randomIndex = rand.nextInt(givenList.size());
					answerDto = questionAnswerDtos.get(givenList.get(randomIndex));
					givenList.remove(randomIndex);	
				}
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
