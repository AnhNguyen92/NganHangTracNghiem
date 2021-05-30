package vn.com.ids.multiplechoice.dao.model.enums;

public enum UserRole {
    ADMIN("Quản trị viên"), //
    INSPECTOR("Người kiểm duyệt"), //
    CREATOR("Người tạo đề thi"), //
    USER("Người dùng");
    
    private final String value;
    
    private UserRole(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
