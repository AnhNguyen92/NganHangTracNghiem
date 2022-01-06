package vn.com.multiplechoice.web.dto;

import java.util.ArrayList;
import java.util.List;

public class ExamDto {
	private String title;
	List<QuestionDto> questions = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDto> questions) {
		this.questions = questions;
	}

}
