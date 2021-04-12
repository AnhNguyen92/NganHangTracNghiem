package vn.com.ids.multiplechoice.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import vn.com.ids.multiplechoice.dao.model.enums.QuestionType;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "question_id")
    private Long id;
    
    @Column(name = "question_content")
    private String questionContent;
    
    @Column(name = "question_suggest")
    private String questionSuggest;
    
    @Column(name = "answer_a")
    private String answerA;

    @Column(name = "answer_b")
    private String answerB;

    @Column(name = "answer_c")
    private String answerC;
    
    @Column(name = "answer_d")
    private String answerD;
    
    @Column(name = "answer_e")
    private String answerE;
    
    @Column(name = "answer_f")
    private String answerF;
    
    @Column(name = "answer_g")
    private String answerG;
    
    @Column(name = "answer_h")
    private String answerH;
    
    @Column(name = "answer_i")
    private String answerI;
    @Column(name = "answer_j")
    private String answerJ;

    @Column(name = "question_id")
    private Long groupId;
    @Column(name = "question_id")
    private Long userId;
    
    
    @Column(name = "right_answer")
    private String rightAnswer;

    @Column(name = "answer_permutation")
    private String answerPemutation;
    
    @Column(name = "question_type")
    private QuestionType questionType;

    
}
