//package vn.com.ids.multiplechoice.dao.model;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "question_group")
//public class QuestionGroup implements Serializable {
//    private static final long serialVersionUID = 8131754660579738809L;
//
//    @Id
//    @Column(name = "group_id")
//    private Long groupId;
//
//    @Id
//    @Column(name = "question_id")
//    private Long questionId;
//
//    @Column(name = "question_position")
//    private String questionPosition;
//
//    public QuestionGroup() {
//    }
//
//    public QuestionGroup(Long groupId, Long questionId, String questionPosition) {
//        super();
//        this.groupId = groupId;
//        this.questionId = questionId;
//        this.questionPosition = questionPosition;
//    }
//
//    public Long getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(Long groupId) {
//        this.groupId = groupId;
//    }
//
//    public Long getQuestionId() {
//        return questionId;
//    }
//
//    public void setQuestionId(Long questionId) {
//        this.questionId = questionId;
//    }
//
//    public String getQuestionPosition() {
//        return questionPosition;
//    }
//
//    public void setQuestionPosition(String questionPosition) {
//        this.questionPosition = questionPosition;
//    }
//
//}
