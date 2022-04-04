package vn.com.multiplechoice.dao.model.enums;

public enum PageSize {
	TEN(10), TWENTY_FIVE(25), TWENTY(50), HUNDRED(100);

	private int value;

	PageSize(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
