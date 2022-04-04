package vn.com.multiplechoice.business.service;

import java.util.List;

import vn.com.multiplechoice.dao.criteria.TestFeedbackCriteria;
import vn.com.multiplechoice.dao.model.TestFeedback;

public interface TestFeedbackService extends IGenericService<TestFeedback, Long> {
	List<TestFeedback> findAll(TestFeedbackCriteria testFeedbackCriteria);
}
