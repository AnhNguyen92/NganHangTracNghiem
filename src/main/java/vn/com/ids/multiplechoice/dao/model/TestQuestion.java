package vn.com.ids.multiplechoice.dao.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test_question")
public class TestQuestion {
    private Long questionId;
    private Long testId;
}
