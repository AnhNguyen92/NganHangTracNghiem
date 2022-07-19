package vn.com.multiplechoice.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.com.multiplechoice.dao.model.HeaderTemplate;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.model.MCQDto;

public class ExamDto {
	private Long id;
	private String title;
	private int numOfQuestions;
	private String executeTime;
	private Date createDate;
	private User creator;
	private HeaderTemplate header;
	private List<MCQDto> questions = new ArrayList<>();
	private double totalScore = 0.0;
	private int totalRightAnswer = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumOfQuestions() {
		return numOfQuestions;
	}

	public void setNumOfQuestions(int numOfQuestions) {
		this.numOfQuestions = numOfQuestions;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public HeaderTemplate getHeader() {
		return header;
	}

	public void setHeader(HeaderTemplate header) {
		this.header = header;
	}

	public List<MCQDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<MCQDto> questions) {
		this.questions = questions;
	}

	public double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}

	public int getTotalRightAnswer() {
		return totalRightAnswer;
	}

	public void setTotalRightAnswer(int totalRightAnswer) {
		this.totalRightAnswer = totalRightAnswer;
	}

	@Override
	public String toString() {
		return "ExamDto [id=" + id + ", title=" + title + ", numOfQuestions=" + numOfQuestions + ", executeTime="
				+ executeTime + ", creator=" + creator + ", header=" + header + ", questions=" + questions
				+ ", totalScore=" + totalScore + ", totalRightAnswer=" + totalRightAnswer + "]";
	}

}
