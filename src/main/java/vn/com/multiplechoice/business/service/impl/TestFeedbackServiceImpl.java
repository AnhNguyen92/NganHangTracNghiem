package vn.com.multiplechoice.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.TestFeedbackService;
import vn.com.multiplechoice.dao.criteria.TestFeedbackCriteria;
import vn.com.multiplechoice.dao.model.TestFeedback;
import vn.com.multiplechoice.dao.repository.TestFeedbackRepository;

@Service
@Transactional
public class TestFeedbackServiceImpl extends AbstractService<TestFeedback, Long> implements TestFeedbackService {
	private EntityManager em;
	private TestFeedbackRepository feedbackRepository;

	@Autowired
	public TestFeedbackServiceImpl(EntityManager em, TestFeedbackRepository feedbackRepository) {
		super(feedbackRepository);
		this.feedbackRepository = feedbackRepository;
		this.em = em;
	}

	@Override
	public List<TestFeedback> findAll(TestFeedbackCriteria testFeedbackCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TestFeedback> criteriaQuery = cb.createQuery(TestFeedback.class);
		Root<TestFeedback> root = criteriaQuery.from(TestFeedback.class);
		List<Predicate> predicates = new ArrayList<>();
		Predicate onStartPredicate = cb.greaterThanOrEqualTo(root.get("createTime"),
				testFeedbackCriteria.getDateRange().getFromDate());
		Predicate onEndPredicate = cb.lessThanOrEqualTo(root.get("createTime"),
				testFeedbackCriteria.getDateRange().getToDate());
		predicates.add(onStartPredicate);
		predicates.add(onEndPredicate);
		String searchText = testFeedbackCriteria.getSearchText();
		if (!StringUtils.isEmpty(searchText)) {
			Predicate searchTextPredicate = cb.or(
					cb.like(root.get("content"), "%" + searchText.trim() + "%"),
					cb.like(root.get("test").get("content"),
							"%" + searchText.trim() + "%"));
			predicates.add(searchTextPredicate);
		}
		criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		TypedQuery<TestFeedback> query = em.createQuery(criteriaQuery);

		return query.getResultList();
	}
}
