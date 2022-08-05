package vn.com.multiplechoice.dao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "exam_result")
public class ExamResult implements Serializable {

	private static final long serialVersionUID = 1674362011796298541L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "execute_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date executeTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "test_id")
	private Test test;

	@OneToMany(mappedBy = "examResult",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ExamResultItem> examReultItems = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public List<ExamResultItem> getExamReultItems() {
		return examReultItems;
	}

	public void setExamReultItems(List<ExamResultItem> examReultItems) {
		this.examReultItems = examReultItems;
	}

	public void addExamReultItems(ExamResultItem examResultItem) {
		examResultItem.setExamResult(this);
		this.examReultItems.add(examResultItem);		
	}

}
