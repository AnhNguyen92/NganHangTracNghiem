package vn.com.multiplechoice.dao.model.enums;

public enum UserStatus {
    ACTIVE("Kích hoạt"), //
    IN_ACTIVE("Chưa kích hoạt");

    private String value;

    private UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
