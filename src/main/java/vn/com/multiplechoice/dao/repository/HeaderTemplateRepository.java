package vn.com.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.multiplechoice.dao.model.HeaderTemplate;

public interface HeaderTemplateRepository extends JpaRepository<HeaderTemplate, Long>, JpaSpecificationExecutor<HeaderTemplate> {

}
