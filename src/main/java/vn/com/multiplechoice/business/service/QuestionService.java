package vn.com.multiplechoice.business.service;

import java.util.List;

import vn.com.multiplechoice.dao.criteria.QuestionCriteria;
import vn.com.multiplechoice.dao.model.Question;

public interface QuestionService extends IGenericService<Question, Long> {

    Object getPage(int pageNumber, int size);

	List<Question> findByAuthor(Long userId);

    List<Question> findAll(QuestionCriteria questionCriteria);

    List<Question> findAllById(List<Long> ids);

}
