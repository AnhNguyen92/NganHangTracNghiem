package vn.com.multiplechoice.dao.model;

import java.util.List;

public class Options {

	private String content;
	private boolean isPublic;
	private List<String> selected;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<String> getSelected() {
		return selected;
	}

	public void setSelected(List<String> selected) {
		this.selected = selected;
	}

}
