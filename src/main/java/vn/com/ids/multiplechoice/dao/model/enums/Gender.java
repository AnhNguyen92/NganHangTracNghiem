package vn.com.ids.multiplechoice.dao.model.enums;

public enum Gender {
    MALE("Nam"), FEMALE("Nữ");

    private final String displayValue;

    private Gender(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
