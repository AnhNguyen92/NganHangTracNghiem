package vn.com.ids.multiplechoice.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "test_feedbacks")
public class TestFeedback implements Serializable {
    private static final long serialVersionUID = -7315036578636778493L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "create_time")
    private LocalDateTime createTime;
    
    @Column(name = "content")
    private String content;
    
    @ManyToOne
    private TestQuestion testQuestion;
    
    @OneToOne
    private User user;

}
