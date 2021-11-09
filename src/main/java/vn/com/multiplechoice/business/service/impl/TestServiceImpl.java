package vn.com.multiplechoice.business.service.impl;

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
        Predicate onStart = cb.greaterThanOrEqualTo(root.get("createDate"), testCriteria.getDateRange().getFromDate());
        Predicate onEnd = cb.lessThanOrEqualTo(root.get("createDate"), testCriteria.getDateRange().getToDate());
        if (!StringUtils.isEmpty(testCriteria.getSearchText())) {
        	cb.like(root.get("content"), testCriteria.getSearchText().trim());
        }
        if (testCriteria.getStatus() != null) {
        	cb.equal(root.get("status"), testCriteria.getStatus());
        }
        criteriaQuery.where(onStart, onEnd);
        TypedQuery<Test> query = em.createQuery(criteriaQuery);
        List<Test> tests = query.getResultList();
        
		return tests;
	}

}
