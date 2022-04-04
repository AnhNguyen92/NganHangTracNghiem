package vn.com.multiplechoice.dao.model.enums;

public enum Gender {
    MALE("Nam"), FEMALE("Nữ");

    private final String value;

    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    
}
