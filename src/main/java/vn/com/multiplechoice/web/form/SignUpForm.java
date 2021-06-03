package vn.com.multiplechoice.web.form;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import vn.com.multiplechoice.dao.model.enums.Gender;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.dao.model.enums.UserStatus;

public class SignUpForm implements Serializable {
    private static final long serialVersionUID = -5852430861347682459L;

    private Long id;
    
    @Length(min = 6, max = 100, message = "Tên đăng nhập phải từ 6 - 100 ký tự")
    private String username;
    
    @Length(max = 50, message = "Tên không được dài quá 50 ký tự")
    private String firstname;
    
    @Length(max = 50, message = "Họ không được dài quá 50 ký tự")
    private String lastname;
    
    @Email(message = "Địa chỉ mail không hợp lệ!")
    @NotEmpty(message = "Địa chỉ mail không được để trống!")
    private String email;
    
    @Length(min = 6, max = 100, message = "Mật khẩu phải từ 6 - 100 ký tự")
    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;
    
    @Length(min = 6, max = 100, message = "Mật khẩu phải từ 6 - 100 ký tự")
    @NotEmpty(message = "Mật khẩu không được để trống")
    private String rePassword;
    
    private String phoneNumber;
    
    private UserStatus status;
    
    private UserRole role;
    
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    
    private Gender gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

}
