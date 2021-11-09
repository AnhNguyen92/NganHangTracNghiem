package vn.com.multiplechoice.dao.model.enums;

public enum PageSize {
	FIVE(5), TEN(10), FIFTEEN(20), TWENTY(50);

	private int size;

	
	private PageSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

}
