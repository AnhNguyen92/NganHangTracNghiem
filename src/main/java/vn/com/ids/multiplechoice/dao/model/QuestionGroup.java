package vn.com.ids.multiplechoice.dao.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "question_group")
public class QuestionGroup {
    private Long groupId;
    private Long questionId;
    private String questionPosition;

    public QuestionGroup() {
    }

    public QuestionGroup(Long groupId, Long questionId, String questionPosition) {
        super();
        this.groupId = groupId;
        this.questionId = questionId;
        this.questionPosition = questionPosition;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionPosition() {
        return questionPosition;
    }

    public void setQuestionPosition(String questionPosition) {
        this.questionPosition = questionPosition;
    }

}
