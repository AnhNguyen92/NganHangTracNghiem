package vn.com.multiplechoice.business.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.criteria.QuestionCriteria;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.repository.QuestionRepository;

@Service
@Transactional
public class QuestionServiceImpl extends AbstractService<Question, Long> implements QuestionService {
    private EntityManager em;
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(EntityManager em, QuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository = questionRepository;
        this.em = em;
    }

	@Override
	public Object getPage(int pageNumber, int size) {
		return null;
	}

	@Override
	public List<Question> findByAuthor(Long userId) {
		return questionRepository.findByAuthor(userId);
	}

    @Override
    public List<Question> findAll(QuestionCriteria questionCriteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Question> criteriaQuery = cb.createQuery(Question.class);
        Root<Question> root = criteriaQuery.from(Question.class);
        if (!StringUtils.isEmpty(questionCriteria.getSearchText())) {
            cb.like(root.get("content"), questionCriteria.getSearchText().trim());
        }
        if (questionCriteria.getType() != null) {
            cb.equal(root.get("questionType"), questionCriteria.getType());
        }
        TypedQuery<Question> query = em.createQuery(criteriaQuery);
        List<Question> questions = query.getResultList();
        
        return questions;
    }
    
}
