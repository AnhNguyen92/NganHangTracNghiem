package vn.com.multiplechoice.dao.model.enums;

public enum PageSize {
	TEN(10), TWENTY_FIVE(25), TWENTY(50), HUNDRED(100);

	private int size;

	private PageSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

}
