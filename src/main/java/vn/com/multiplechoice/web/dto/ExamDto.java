package vn.com.multiplechoice.web.dto;

import java.util.HashSet;
import java.util.Set;

import vn.com.multiplechoice.dao.model.HeaderTemplate;
import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.web.model.MCQDto;

public class ExamDto {
	private String title;
	private int numOfQuestions;
	private String executeTime;
	private User creator;
	private HeaderTemplate header;
	private Set<MCQDto> questions = new HashSet<>();

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

	public Set<MCQDto> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<MCQDto> questions) {
		this.questions = questions;
	}

}
