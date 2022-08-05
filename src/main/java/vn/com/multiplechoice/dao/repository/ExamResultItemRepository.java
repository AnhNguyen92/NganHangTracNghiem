package vn.com.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.multiplechoice.dao.model.ExamResultItem;

public interface ExamResultItemRepository
		extends JpaRepository<ExamResultItem, Long>, JpaSpecificationExecutor<ExamResultItem> {
}
