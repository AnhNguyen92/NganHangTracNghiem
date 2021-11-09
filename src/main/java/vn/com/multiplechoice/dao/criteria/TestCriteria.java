package vn.com.multiplechoice.dao.criteria;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import vn.com.multiplechoice.dao.model.enums.PageSize;
import vn.com.multiplechoice.dao.model.enums.TestStatus;

public class TestCriteria {
	private String searchText;
	private TestStatus status;
	private PageSize size;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fromDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date toDate;

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

	public PageSize getSize() {
		return size;
	}

	public void setSize(PageSize size) {
		this.size = size;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

}
