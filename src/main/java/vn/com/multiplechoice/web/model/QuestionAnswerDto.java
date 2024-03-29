package vn.com.multiplechoice.web.model;

public class QuestionAnswerDto {
	private int order;
	private boolean randomPosition = true;
	private String answerLabel;
	private String originalLabel;
	private String answerContent;
	private boolean trueAnswer = false;
	private int score = 0;
	private boolean leftSide = true;
	private boolean isSelected;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isRandomPosition() {
		return randomPosition;
	}

	public void setRandomPosition(boolean randomPosition) {
		this.randomPosition = randomPosition;
	}

	public String getAnswerLabel() {
		return answerLabel;
	}

	public void setAnswerLabel(String answerLabel) {
		this.answerLabel = answerLabel;
	}

	public String getOriginalLabel() {
        return originalLabel;
    }

    public void setOriginalLabel(String originalLabel) {
        this.originalLabel = originalLabel;
    }

    public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public boolean getTrueAnswer() {
		return trueAnswer;
	}

	public void setTrueAnswer(boolean trueAnswer) {
		this.trueAnswer = trueAnswer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isLeftSide() {
		return leftSide;
	}

	public void setLeftSide(boolean leftSide) {
		this.leftSide = leftSide;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "QuestionAnswerDto [order=" + order + ", randomPosition=" + randomPosition + ", answerLabel="
				+ answerLabel + ", answerContent=" + answerContent + ", trueAnswer=" + trueAnswer + ", score=" + score
				+ ", leftSide=" + leftSide + ", isSelected=" + isSelected + "]";
	}

}
