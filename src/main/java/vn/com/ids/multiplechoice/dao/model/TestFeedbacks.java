package vn.com.ids.multiplechoice.dao.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test_feedbacks")
public class TestFeedbacks {
    private Long id;
    private String content;
    private Long testId;

}
