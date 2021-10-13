package vn.com.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.multiplechoice.dao.model.Test;

public interface TestRepository extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test> {

}
