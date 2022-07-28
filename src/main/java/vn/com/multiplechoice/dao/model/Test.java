package vn.com.multiplechoice.dao.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import vn.com.multiplechoice.dao.model.enums.TestStatus;

@Table(name = "test")
@Entity
public class Test implements Serializable {

	private static final long serialVersionUID = -5540956405687315750L;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	@Column(name = "content")
	private String content;

	@Column(name = "num_of_question")
	private int numOfQuestions;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private TestStatus status;

	@Column(name = "execute_time")
	private String executeTime;

	@Column(name = "is_public")
	private boolean isPublic;

	@Column(name = "approved_date")
    private Date approvedDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User creator;

	@ManyToOne
	@JoinColumn(name = "inspector")
	private User inspector;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "test_question", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
	private Set<Question> questions = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "header_template_id")
	private HeaderTemplate header;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "test")
	private List<TestFeedback> feedbacks;

	public void addQuestion(Question question) {
	    this.numOfQuestions++;
		questions.add(question);
		question.getTests().add(this);
    }
 
    public void removeQuestion(Question question) {
        this.numOfQuestions--;
    	questions.remove(question);
        question.getTests().remove(this);
    }
    
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

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public void setNumOfQuestions(int numOfQuestions) {
		this.numOfQuestions = numOfQuestions;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getInspector() {
		return inspector;
	}

	public void setInspector(User inspector) {
		this.inspector = inspector;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public HeaderTemplate getHeader() {
		return header;
	}

	public void setHeader(HeaderTemplate header) {
		this.header = header;
	}

	public List<TestFeedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<TestFeedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

}
