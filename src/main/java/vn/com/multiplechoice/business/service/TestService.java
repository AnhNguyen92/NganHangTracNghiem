package vn.com.multiplechoice.business.service;

import java.util.List;

import vn.com.multiplechoice.dao.criteria.TestCriteria;
import vn.com.multiplechoice.dao.model.Test;

public interface TestService extends IGenericService<Test, Long> {

	List<Test> findAll(TestCriteria testCriteria);

	List<Test> findByUserId(Long userId);
}
