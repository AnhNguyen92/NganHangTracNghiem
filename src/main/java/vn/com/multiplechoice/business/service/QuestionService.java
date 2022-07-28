package vn.com.multiplechoice.business.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import vn.com.multiplechoice.dao.criteria.QuestionCriteria;
import vn.com.multiplechoice.dao.model.Question;

public interface QuestionService extends IGenericService<Question, Long> {

	List<Question> findByAuthor(Long userId);

    List<Question> findAll(QuestionCriteria questionCriteria);

    List<Question> findAllById(List<Long> ids);

    Page<Question> findAllByUserId(Long userId, Pageable pageable);

}
