package vn.com.ids.multiplechoice.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_templates")
public class UserTemplate implements Serializable {
    private static final long serialVersionUID = -7840237809611717075L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long templateId;
    
    @ManyToOne
    private User user;
    @Column
    private String name;
    @Column
    private String sourcePath;

}
