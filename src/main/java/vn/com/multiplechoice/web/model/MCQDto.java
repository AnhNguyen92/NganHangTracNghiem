package vn.com.multiplechoice.web.model;

import java.util.List;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.enums.QuestionType;

public class MCQDto {
    private QuestionType type;
    private User user;
    private String answerSuggestion;
    private String content;
    // list answer of question
    private List<QuestionAnswerDto> questionAnswerDtos;

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAnswerSuggestion() {
        return answerSuggestion;
    }

    public void setAnswerSuggestion(String answerSuggestion) {
        this.answerSuggestion = answerSuggestion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<QuestionAnswerDto> getQuestionAnswerDtos() {
        return questionAnswerDtos;
    }

    public void setQuestionAnswerDtos(List<QuestionAnswerDto> questionAnswerDtos) {
        this.questionAnswerDtos = questionAnswerDtos;
    }

}
