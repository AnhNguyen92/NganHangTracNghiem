package vn.com.multiplechoice.dao.criteria;

import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.enums.PageSize;
import vn.com.multiplechoice.dao.model.enums.TestStatus;

public class TestCriteria {
	private String authorName;
	private TestStatus status;
	private PageSize size;
	private DateRange dateRange = new DateRange();

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public PageSize getSize() {
		return size;
	}

	public void setSize(PageSize size) {
		this.size = size;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

}
