package vn.com.multiplechoice.web.dto;

import java.util.List;

public class ExamResultItemDTO {
	private String questionContent;
	private String type;
	private List<String> rightAnswer;
	private List<String> selectAnswer;
	private int count;
	private int score;
	private String explain;

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(List<String> rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public List<String> getSelectAnswer() {
		return selectAnswer;
	}

	public void setSelectAnswer(List<String> selectAnswer) {
		this.selectAnswer = selectAnswer;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

}
