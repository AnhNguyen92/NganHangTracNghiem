package vn.com.multiplechoice.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "test")
@Entity
public class Test implements Serializable {

    private static final long serialVersionUID = -5540956405687315750L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    @Column(name = "content")
    private String content;

    @Column(name = "num_of_question")
    private int numOfQuestions;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creator;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
