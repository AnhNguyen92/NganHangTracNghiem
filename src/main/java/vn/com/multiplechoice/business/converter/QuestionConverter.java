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
	private static final String[] ANSWER_LABEL_LIST = new String[] { "A", "B", "C", "D", "E", "F", "G", "H" };

	@Autowired
	private OnlineUserUtil onlineUserUtil;

	public MCQDto toDto(Question question) {
		MCQDto dto = new MCQDto();
		dto.setId(question.getId());
		dto.setUser(question.getUser());
		dto.setAnswerSuggestion(question.getSuggest());
		dto.setContent(question.getContent());
		dto.setAnswerSuggestion(question.getSuggest());
		QuestionType questionType = question.getType();
		dto.setType(questionType);
		if (QuestionType.MATCHING.equals(questionType)) {
			dto.setLeftAnswerDtos(convertLeftAnswers(question));
			dto.setRightAnswerDtos(convertRightAnswers(question));
		} else {
			dto.setQuestionAnswerDtos(convertQuestionAnswer(question));
		}

		return dto;
	}

	private List<QuestionAnswerDto> convertRightAnswers(Question question) {
		List<QuestionAnswerDto> righttAnswerDtos = new ArrayList<>();
        QuestionAnswerDto answerDto;
        int totalLeftAnswer = question.getScore().replace(",", "").length();
        int order = 0;
        
        // map answer B
        if (question.getAnswerB() != null && totalLeftAnswer < 2) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order);
            answerDto.setAnswerLabel(ANSWER_LABEL_LIST[order++]);
            answerDto.setAnswerContent(question.getAnswerB());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("B"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("B"));
            righttAnswerDtos.add(answerDto);
        }
        // map answer C
        if (question.getAnswerC() != null && totalLeftAnswer < 3) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order);
            answerDto.setAnswerLabel(ANSWER_LABEL_LIST[order++]);
            answerDto.setAnswerContent(question.getAnswerC());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("C"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("C"));
            righttAnswerDtos.add(answerDto);
        }
        // map answer D
        if (question.getAnswerD() != null && totalLeftAnswer < 4) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order);
            answerDto.setAnswerLabel(ANSWER_LABEL_LIST[order++]);
            answerDto.setAnswerContent(question.getAnswerD());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("D"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("D"));
            righttAnswerDtos.add(answerDto);
        }
        // map answer E
        if (question.getAnswerE() != null && totalLeftAnswer < 5) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order);
            answerDto.setAnswerLabel(ANSWER_LABEL_LIST[order++]);
            answerDto.setAnswerContent(question.getAnswerE());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("E"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("E"));
            righttAnswerDtos.add(answerDto);
        }
        // map answer F
        if (question.getAnswerF() != null && totalLeftAnswer < 6) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order);
            answerDto.setAnswerLabel(ANSWER_LABEL_LIST[order++]);
            answerDto.setAnswerContent(question.getAnswerF());
            answerDto.setTrueAnswer(question.getRightAnswer().equals("F"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("F"));
            righttAnswerDtos.add(answerDto);
        }
        // map answer G
        if (question.getAnswerG() != null) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order);
            answerDto.setAnswerLabel(ANSWER_LABEL_LIST[order++]);
            answerDto.setAnswerContent(question.getAnswerG());
            answerDto.setTrueAnswer(question.getRightAnswer().equals("G"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("G"));
            righttAnswerDtos.add(answerDto);
        }
        // map answer H
        if (question.getAnswerH() != null) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order);
            answerDto.setAnswerLabel(ANSWER_LABEL_LIST[order]);
            answerDto.setAnswerContent(question.getAnswerH());
            answerDto.setTrueAnswer(question.getRightAnswer().equals("H"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("H"));
            righttAnswerDtos.add(answerDto);
        }
        
		return righttAnswerDtos;
	}

	private List<QuestionAnswerDto> convertLeftAnswers(Question question) {
		List<QuestionAnswerDto> leftAnswerDtos = new ArrayList<>();
		QuestionAnswerDto answerDto;

		int totalLeftAnswer = question.getScore().replace(",", "").length();
        int order = 0;
        // map answer A
        if (question.getAnswerA() != null) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order++);
            answerDto.setAnswerLabel("" + order);
            answerDto.setAnswerContent(question.getAnswerA());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("A"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("A"));
            leftAnswerDtos.add(answerDto);
        }
        // map answer B
        if (question.getAnswerB() != null && totalLeftAnswer > 1) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order++);
            answerDto.setAnswerLabel("" + order);
            answerDto.setAnswerContent(question.getAnswerB());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("B"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("B"));
            leftAnswerDtos.add(answerDto);
        }
        // map answer C
        if (question.getAnswerC() != null && totalLeftAnswer > 2) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order++);
            answerDto.setAnswerLabel("" + order);
            answerDto.setAnswerContent(question.getAnswerC());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("C"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("C"));
            leftAnswerDtos.add(answerDto);
        }
        // map answer D
        if (question.getAnswerD() != null && totalLeftAnswer > 3) {
            answerDto = new QuestionAnswerDto();
            answerDto.setOrder(order++);
            answerDto.setAnswerLabel("" + order);
            answerDto.setAnswerContent(question.getAnswerD());
            answerDto.setTrueAnswer(question.getRightAnswer().contains("D"));
            answerDto.setRandomPosition(question.getAnswerPemutation().contains("D"));
            leftAnswerDtos.add(answerDto);
        }
        
		return leftAnswerDtos;
	}

	public Question toEntity(MCQDto mcqDto) {
		Question entity = new Question();

		User user = onlineUserUtil.getOnlineUser();
		entity.setId(mcqDto.getId());
		entity.setUser(user);
		entity.setType(mcqDto.getType());
		entity.setContent(mcqDto.getContent());
		entity.setSuggest(mcqDto.getAnswerSuggestion());
		if (QuestionType.FILLING.equals(mcqDto.getType())) {
			mapFillingQuestion(entity, mcqDto);
		} else if (QuestionType.GROUP_FILLING.equals(mcqDto.getType())) {
		    // not implement yet
		} else if (QuestionType.MATCHING.equals(mcqDto.getType())) {
			mapMatchingAnswerQuestion(entity, mcqDto);
		} else if (QuestionType.MULTIPLE_ANSWER.equals(mcqDto.getType())) {
			mapMultipleAnswerQuestion(entity, mcqDto);
		} else if (QuestionType.ONE_ANSWER.equals(mcqDto.getType())) {
			mapOneAnswerQuestion(entity, mcqDto);
		} else if (QuestionType.TRUE_FALSE.equals(mcqDto.getType())) {
			mapTrueFalseQuestion(entity, mcqDto);
		} else if (QuestionType.UNDERLINE.equals(mcqDto.getType())) {
			mapUnderlineQuestion(entity, mcqDto);
		} else if (QuestionType.YES_NO.equals(mcqDto.getType())) {
			mapYesNoQuestion(entity, mcqDto);
		}

		return entity;
	}

	private void mapFillingQuestion(Question entity, MCQDto mcqDto) {
		List<QuestionAnswerDto> questionAnswerDtos = new ArrayList<>();
		questionAnswerDtos.addAll(mcqDto.getLeftAnswerDtos());
		questionAnswerDtos.addAll(mcqDto.getRightAnswerDtos());
		saveQuestionAnswer(entity, questionAnswerDtos);
		String permutationPosLst = questionAnswerDtos.stream().filter(QuestionAnswerDto::isRandomPosition)
				.map(answer -> ANSWER_LABEL_LIST[questionAnswerDtos.indexOf(answer)]).collect(Collectors.joining(","));
		entity.setAnswerPemutation(permutationPosLst);
		entity.setScore(mcqDto.getLeftAnswerDtos().get(0).getScore() + "");
		entity.setRightAnswer("A");

	}

	private void mapUnderlineQuestion(Question entity, MCQDto mcqDto) {
		String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(","));
		entity.setAnswerPemutation(permutationPosLst);
		saveQuestionAnswer(entity, mcqDto.getQuestionAnswerDtos());
		String rightAnswerStr = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::getTrueAnswer)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(","));
		entity.setRightAnswer(rightAnswerStr);
	}

	private void mapTrueFalseQuestion(Question entity, MCQDto mcqDto) {
		saveQuestionAnswer(entity, mcqDto.getQuestionAnswerDtos());
		String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(","));
		entity.setAnswerPemutation(permutationPosLst);
		entity.setRightAnswer(mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::getTrueAnswer)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(",")));
		entity.setScore(mcqDto.getQuestionAnswerDtos().stream().map(answerDto -> String.valueOf(answerDto.getScore()))
				.collect(Collectors.joining(",")));
	}

	private void mapYesNoQuestion(Question entity, MCQDto mcqDto) {
		String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(","));
		entity.setAnswerPemutation(permutationPosLst);
		List<QuestionAnswerDto> questionAnswerDtos = mcqDto.getQuestionAnswerDtos();
		entity.setAnswerA(questionAnswerDtos.get(0).getAnswerContent());
		entity.setAnswerB(questionAnswerDtos.get(1).getAnswerContent());
		Optional<QuestionAnswerDto> trueAnsPosOptinal = mcqDto.getQuestionAnswerDtos().stream()
				.filter(QuestionAnswerDto::getTrueAnswer).findFirst();
		int trueAnswerPos = trueAnsPosOptinal.isPresent() ? trueAnsPosOptinal.get().getOrder() : 0;
		entity.setRightAnswer(ANSWER_LABEL_LIST[trueAnswerPos]);
	}

	private void mapMatchingAnswerQuestion(Question entity, MCQDto mcqDto) {
		// score sai
		List<QuestionAnswerDto> rightAnswerDtos = mcqDto.getRightAnswerDtos();
		List<QuestionAnswerDto> leftAnswerDtos = mcqDto.getLeftAnswerDtos();
		String permutationPosLst = rightAnswerDtos.stream().filter(QuestionAnswerDto::isRandomPosition)
				.map(answer -> ANSWER_LABEL_LIST[leftAnswerDtos.size() + answer.getOrder()])
				.collect(Collectors.joining(","));
		entity.setAnswerPemutation(permutationPosLst);
		entity.setScore(leftAnswerDtos.stream().map(answerDto -> String.valueOf(answerDto.getScore()))
				.collect(Collectors.joining(",")));
		List<QuestionAnswerDto> trueAnswerDtos = rightAnswerDtos.subList(0, leftAnswerDtos.size());
		entity.setRightAnswer(trueAnswerDtos.stream()
				.map(answer -> ANSWER_LABEL_LIST[mcqDto.getLeftAnswerDtos().size() + answer.getOrder()])
				.collect(Collectors.joining(",")));
		List<QuestionAnswerDto> questionAnswerDtos = new ArrayList<>();
		questionAnswerDtos.addAll(leftAnswerDtos);
		questionAnswerDtos.addAll(rightAnswerDtos);
		saveQuestionAnswer(entity, questionAnswerDtos);

	}

	private void mapMultipleAnswerQuestion(Question entity, MCQDto mcqDto) {
		String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(","));
		entity.setAnswerPemutation(permutationPosLst);
		saveQuestionAnswer(entity, mcqDto.getQuestionAnswerDtos());
		entity.setRightAnswer(mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::getTrueAnswer)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(",")));
		entity.setScore(mcqDto.getQuestionAnswerDtos().stream().map(answerDto -> String.valueOf(answerDto.getScore()))
				.collect(Collectors.joining(",")));
	}

	private void mapOneAnswerQuestion(Question question, MCQDto mcqDto) {
		saveQuestionAnswer(question, mcqDto.getQuestionAnswerDtos());

		Optional<QuestionAnswerDto> trueAnsPosOptinal = mcqDto.getQuestionAnswerDtos().stream()
				.filter(QuestionAnswerDto::getTrueAnswer).findFirst();
		int trueAnswerPos = trueAnsPosOptinal.isPresent() ? trueAnsPosOptinal.get().getOrder() : 0;
		question.setRightAnswer(ANSWER_LABEL_LIST[trueAnswerPos]);
		String permutationPosLst = mcqDto.getQuestionAnswerDtos().stream().filter(QuestionAnswerDto::isRandomPosition)
				.map(answer -> ANSWER_LABEL_LIST[answer.getOrder()]).collect(Collectors.joining(","));
		logger.info(permutationPosLst);
		question.setAnswerPemutation(permutationPosLst);
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

	private List<QuestionAnswerDto> convertQuestionAnswer(Question question) {
		List<QuestionAnswerDto> questionAnswerDtos = new ArrayList<>();
	    
		QuestionAnswerDto answerDto;

		int order = 0;
		// map answer A
		String answerPemutation = question.getAnswerPemutation();
        if (question.getAnswerA() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order++);
			answerDto.setAnswerLabel("A");
			answerDto.setScore(100);
			answerDto.setAnswerContent(question.getAnswerA());
			answerDto.setTrueAnswer(question.getRightAnswer().contains("A"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("A"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer B
		if (question.getAnswerB() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order++);
			
			answerDto.setAnswerContent(question.getAnswerB());
			answerDto.setTrueAnswer(question.getRightAnswer().contains("B"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("B"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer C
		if (question.getAnswerC() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order++);
			
			answerDto.setAnswerContent(question.getAnswerC());
			answerDto.setTrueAnswer(question.getRightAnswer().contains("C"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("C"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer D
		if (question.getAnswerD() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order++);
			
			answerDto.setAnswerContent(question.getAnswerD());
			answerDto.setTrueAnswer(question.getRightAnswer().contains("D"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("D"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer E
		if (question.getAnswerE() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order++);
			
			answerDto.setAnswerContent(question.getAnswerE());
			answerDto.setTrueAnswer(question.getRightAnswer().contains("E"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("E"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer F
		if (question.getAnswerF() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order++);
			
			answerDto.setAnswerContent(question.getAnswerF());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("F"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("F"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer G
		if (question.getAnswerG() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order++);
			
			answerDto.setAnswerContent(question.getAnswerG());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("G"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("G"));
			questionAnswerDtos.add(answerDto);
		}
		// map answer H
		if (question.getAnswerH() != null) {
			answerDto = new QuestionAnswerDto();
			answerDto.setOrder(order);
			
			answerDto.setAnswerContent(question.getAnswerH());
			answerDto.setTrueAnswer(question.getRightAnswer().equals("H"));
			answerDto.setRandomPosition(answerPemutation != null && answerPemutation.contains("H"));
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
				answerDto.setAnswerLabel(ANSWER_LABEL_LIST[i]);
				answerDto.setOrder(i);
				result.add(answerDto);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
		}

		return result;
	}
}
