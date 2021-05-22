package vn.com.ids.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.ids.multiplechoice.dao.model.TestFeedback;

public interface TestFeedbackRepository extends JpaRepository<TestFeedback, Long>, JpaSpecificationExecutor<TestFeedback> {

}
