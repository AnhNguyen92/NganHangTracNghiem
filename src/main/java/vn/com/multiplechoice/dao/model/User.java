package vn.com.multiplechoice.dao.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import vn.com.multiplechoice.dao.model.enums.Gender;
import vn.com.multiplechoice.dao.model.enums.UserRole;
import vn.com.multiplechoice.dao.model.enums.UserStatus;

@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1059771629296419600L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @Length(max = 50, message = "Tên không được dài quá 50 ký tự")
    private String firstname;

    @Column(name = "last_name")
    @Length(max = 50, message = "Họ không được dài quá 50 ký tự")
    private String lastname;

    @Column(name = "username")
    @Length(min = 6, max = 100, message = "Tên đăng nhập phải từ 6 - 100 ký tự")
    private String username;

    @Column(name = "email")
    @Email(message = "Địa chỉ mail không hợp lệ!")
    @NotEmpty(message = "Địa chỉ mail không được để trống!")
    private String email;

    @Column(name = "password")
    @Length(min = 6, max = 100, message = "Mật khẩu phải từ 6 - 100 ký tự")
    private String password;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private UserStatus status;

    @Column(name = "birthday")
    private LocalDate birthday;

    private Gender gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
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
