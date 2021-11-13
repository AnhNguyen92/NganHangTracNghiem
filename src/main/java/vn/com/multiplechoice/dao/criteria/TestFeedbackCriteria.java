package vn.com.multiplechoice.dao.criteria;

import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.enums.PageSize;

public class TestFeedbackCriteria {
	private String searchText;
	private DateRange dateRange;
	private PageSize size;

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public DateRange getDateRange() {
		return dateRange;
	}

	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

	public PageSize getSize() {
		return size;
	}

	public void setSize(PageSize size) {
		this.size = size;
	}

}
