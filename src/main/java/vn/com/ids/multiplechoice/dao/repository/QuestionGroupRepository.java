package vn.com.ids.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.ids.multiplechoice.dao.model.QuestionGroup;

public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long>, JpaSpecificationExecutor<QuestionGroup> {

}
