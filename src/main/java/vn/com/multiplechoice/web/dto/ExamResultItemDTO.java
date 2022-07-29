package vn.com.multiplechoice.web.dto;

import java.util.List;

public class ExamResultItemDTO {
    private String questionContent;
    private List<String> rightAnswer;
    private List<String> selectAnswer;
    private int count;
    private int score;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
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

}
