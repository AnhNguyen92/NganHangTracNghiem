package vn.com.ids.multiplechoice.web.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleChoiceQuestion {
    
    private String question;
    // list answer of question
    private List<String> answer;
    // list checkbox to know which one answer is corect.
    private List<String> checkboxLst;
    
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public List<String> getAnswer() {
		return answer;
	}
	
	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

    public List<String> getCheckbox() {
        return checkboxLst;
    }

    public void setCheckbox(List<String> checkboxLst) {
        this.checkboxLst = checkboxLst;
    }

    public MultipleChoiceQuestion() {
        this.question = "";
        this.answer  = new ArrayList<String>();
        this.checkboxLst = Arrays.asList("A", "B", "C", "D");
    }
    
}
