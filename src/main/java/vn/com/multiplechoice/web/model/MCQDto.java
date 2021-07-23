package vn.com.multiplechoice.web.model;

import java.util.Arrays;
import java.util.List;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.enums.QuestionType;

public class MCQDto {
    private QuestionType type;
    private User user;
    private String answerSuggestion;
    private String content;
    // list answer of question
    private List<QuestionAnswerDto> answerDtos = Arrays.asList(new QuestionAnswerDto[8]);

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

    public List<QuestionAnswerDto> getAnswerDtos() {
        return answerDtos;
    }

    public void setAnswerDtos(List<QuestionAnswerDto> answerDtos) {
        this.answerDtos = answerDtos;
    }

}
