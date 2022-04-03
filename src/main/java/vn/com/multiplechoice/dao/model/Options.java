package vn.com.multiplechoice.dao.model;

import java.util.List;

public class Options {

    private Long testId;
	private String content;
	private boolean isPublic = true;
	private List<Long> selected;
	private String executeTime;

	
	public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

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

	public List<Long> getSelected() {
		return selected;
	}

	public void setSelected(List<Long> selected) {
		this.selected = selected;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

}
