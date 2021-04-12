package vn.com.ids.multiplechoice.dao.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole {
    private Long roleId;
    @Column
    private String roleName;
}
