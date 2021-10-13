package vn.com.multiplechoice.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.com.multiplechoice.dao.model.enums.QuestionType;

@Entity
@Table(name = "questions")
public class Question implements Serializable {
	private static final long serialVersionUID = -829249034425712258L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "content")
	private String content;

	@Column(name = "suggest")
	private String suggest;

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

	@Column(name = "group_id")
	private Long groupId;

	@Column(name = "right_answer")
	private String rightAnswer;

	@Column(name = "answer_permutation")
	private String answerPemutation;

	@Column(name = "question_type")
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(mappedBy = "questions")
	private List<Test> tests = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getAnswerPemutation() {
		return answerPemutation;
	}

	public void setAnswerPemutation(String answerPemutation) {
		this.answerPemutation = answerPemutation;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Test> getTests() {
		return tests;
	}

	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

}
