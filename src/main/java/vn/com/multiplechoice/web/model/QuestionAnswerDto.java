package vn.com.multiplechoice.web.model;

public class QuestionAnswerDto {
    private int order;
    private boolean randomPosition = true;
    private String answerLabel;
    private String answerContent;
    private boolean trueAnswer = false;
    private int score;

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

}
