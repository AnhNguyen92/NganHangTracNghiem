package vn.com.multiplechoice.web.form;

import org.hibernate.validator.constraints.Length;

public class LoginForm {
    @Length(min = 6, max = 100, message = "Tên đăng nhập phải từ 6 - 100 ký tự")
    private String username;
    @Length(min = 6, max = 100, message = "Mật khẩu phải từ 6 - 100 ký tự")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
