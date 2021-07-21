package vn.com.multiplechoice.web.model;

public class QuestionAnswerDto {
    private int order;
    private boolean randomPosition;
    private String content;
    private boolean isTrueAnswer;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTrueAnswer() {
        return isTrueAnswer;
    }

    public void setTrueAnswer(boolean isTrueAnswer) {
        this.isTrueAnswer = isTrueAnswer;
    }

}
