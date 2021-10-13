package vn.com.multiplechoice.dao.model;

import java.util.List;

public class Options {

	private String content;

	private List<String> selected;

	public List<String> getSelected() {
		return selected;
	}

	public void setSelected(List<String> selected) {
		this.selected = selected;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
