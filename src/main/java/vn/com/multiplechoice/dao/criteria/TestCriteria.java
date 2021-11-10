package vn.com.multiplechoice.dao.criteria;

import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.enums.PageSize;
import vn.com.multiplechoice.dao.model.enums.TestStatus;

public class TestCriteria {
	private String searchText;
	private TestStatus status;
	private long startPage;
	private PageSize size;
	private DateRange dateRange;

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public TestStatus getStatus() {
		return status;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}

	public long getStartPage() {
		return startPage;
	}

	public void setStartPage(long startPage) {
		this.startPage = startPage;
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
