package vn.com.multiplechoice.web.dto;

import java.util.ArrayList;
import java.util.List;

import vn.com.multiplechoice.dao.model.HeaderTemplate;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.model.MCQDto;

public class ExamDto {
	private Long id;
	private String title;
	private int numOfQuestions;
	private String executeTime;
	private User creator;
	private HeaderTemplate header;
	private List<MCQDto> questions = new ArrayList<>();

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

	@Override
	public String toString() {
		return "ExamDto [id=" + id + ", title=" + title + ", numOfQuestions=" + numOfQuestions + ", executeTime="
				+ executeTime + ", creator=" + creator + ", header=" + header + ", questions=" + questions + "]";
	}

}
