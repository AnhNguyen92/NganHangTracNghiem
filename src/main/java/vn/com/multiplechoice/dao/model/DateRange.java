package vn.com.multiplechoice.dao.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DateRange {
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fromDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date toDate;

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
