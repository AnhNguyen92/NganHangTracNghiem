package vn.com.multiplechoice.web.dto;

import java.util.List;

public class QuestionDto {
	private String content;
	private String choices;
	private String answerA;
	private String answerB;
	private String answerC;
	private String answerD;
	private String answerE;
	private String answerF;
	private String answerG;
	private String answerH;
	private boolean ischosen;
	private String selectedAnswer;
	private String rightAnswer;
	private String score;
	private List<String> answers;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getChoices() {
		return choices;
	}

	public void setChoices(String choices) {
		this.choices = choices;
	}

	public String getAnswerA() {
		return answerA;
	}

	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}

	public String getAnswerB() {
		return answerB;
	}

	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}

	public String getAnswerC() {
		return answerC;
	}

	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}

	public String getAnswerD() {
		return answerD;
	}

	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}

	public String getAnswerE() {
		return answerE;
	}

	public void setAnswerE(String answerE) {
		this.answerE = answerE;
	}

	public String getAnswerF() {
		return answerF;
	}

	public void setAnswerF(String answerF) {
		this.answerF = answerF;
	}

	public String getAnswerG() {
		return answerG;
	}

	public void setAnswerG(String answerG) {
		this.answerG = answerG;
	}

	public String getAnswerH() {
		return answerH;
	}

	public void setAnswerH(String answerH) {
		this.answerH = answerH;
	}

	public boolean isIschosen() {
		return ischosen;
	}

	public void setIschosen(boolean ischosen) {
		this.ischosen = ischosen;
	}

	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	public void setSelectedAnswer(String selectedAnswer) {
		this.selectedAnswer = selectedAnswer;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

}
