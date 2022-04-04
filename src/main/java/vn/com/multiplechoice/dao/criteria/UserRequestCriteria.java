package vn.com.multiplechoice.dao.criteria;

import vn.com.multiplechoice.dao.model.DateRange;
import vn.com.multiplechoice.dao.model.enums.PageSize;
import vn.com.multiplechoice.dao.model.enums.UserRequestStatus;

public class UserRequestCriteria {
	private String searchText;
	private UserRequestStatus status;
	private PageSize size;
	private DateRange dateRange;

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public UserRequestStatus getStatus() {
		return status;
	}

	public void setStatus(UserRequestStatus status) {
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

	@Override
	public String toString() {
		return "UserRequestCriteria [searchText=" + searchText + ", status=" + status + ", size=" + size
				+ ", dateRange=" + dateRange + "]";
	}

}
