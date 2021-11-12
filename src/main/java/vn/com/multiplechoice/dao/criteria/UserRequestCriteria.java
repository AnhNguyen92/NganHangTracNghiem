package vn.com.multiplechoice.dao.criteria;

import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.enums.PageSize;
import vn.com.multiplechoice.dao.model.enums.TestStatus;

public class UserRequestCriteria {
	private TestStatus status;
	private PageSize size;
	private DateRange dateRange;

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
