package vn.com.multiplechoice.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import vn.com.multiplechoice.dao.model.Test;

public interface TestRepository extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test> {

	@Query(value = "select * from test where user_id = :userId", nativeQuery = true)
	List<Test> findByUserId(Long userId);

}
