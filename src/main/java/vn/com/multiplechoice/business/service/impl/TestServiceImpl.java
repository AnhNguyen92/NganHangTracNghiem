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
import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.criteria.TestCriteria;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.repository.TestRepository;

@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test, Long> implements TestService {
	private EntityManager em;
	private TestRepository testRepository;

	@Autowired
	public TestServiceImpl(EntityManager em, TestRepository testRepository) {
		super(testRepository);
		this.testRepository = testRepository;
		this.em = em;
	}

	@Override
	public List<Test> findAll(TestCriteria testCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Test> criteriaQuery = cb.createQuery(Test.class);
		Root<Test> root = criteriaQuery.from(Test.class);
		List<Predicate> predicates = new ArrayList<>();
		Predicate onStartPredicate = cb.greaterThanOrEqualTo(root.get("createDate"),
				testCriteria.getDateRange().getFromDate());
		Predicate onEndPredicate = cb.lessThanOrEqualTo(root.get("createDate"),
				testCriteria.getDateRange().getToDate());
		predicates.add(onStartPredicate);
		predicates.add(onEndPredicate);
		String searchText = testCriteria.getSearchText();
		if (!StringUtils.isEmpty(searchText)) {
			Predicate searchTextPredicate = cb.or(cb.like(root.get("content"), "%" + searchText.trim() + "%"),
					cb.like(cb.concat(cb.concat(root.get("creator").get("lastname"), " "),
							root.get("creator").get("firstname")), "%" + searchText.trim() + "%"));
			predicates.add(searchTextPredicate);
		}
		if (testCriteria.getStatus() != null) {
			Predicate statusPredicate = cb.equal(root.get("status"), testCriteria.getStatus());
			predicates.add(statusPredicate);
		}
		criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		TypedQuery<Test> query = em.createQuery(criteriaQuery);

		return query.getResultList();
	}

}
