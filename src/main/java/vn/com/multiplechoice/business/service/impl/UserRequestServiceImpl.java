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

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.UserRequestService;
import vn.com.multiplechoice.dao.criteria.UserRequestCriteria;
import vn.com.multiplechoice.dao.model.UserRequest;
import vn.com.multiplechoice.dao.repository.UserRequestRepository;

@Service
@Transactional
public class UserRequestServiceImpl extends AbstractService<UserRequest, Long> implements UserRequestService {
	private EntityManager em;
	private UserRequestRepository userRequestRepository;

	@Autowired
	public UserRequestServiceImpl(EntityManager em, UserRequestRepository userRequestRepository) {
		super(userRequestRepository);
		this.userRequestRepository = userRequestRepository;
		this.em = em;
	}

	@Override
	public List<UserRequest> findAll(UserRequestCriteria criteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserRequest> criteriaQuery = cb.createQuery(UserRequest.class);
		Root<UserRequest> root = criteriaQuery.from(UserRequest.class);
		List<Predicate> predicates = new ArrayList<>();
		Predicate onStartPredicate = cb.greaterThanOrEqualTo(root.get("createDate"),
				criteria.getDateRange().getFromDate());
		Predicate onEndPredicate = cb.lessThanOrEqualTo(root.get("createDate"), criteria.getDateRange().getToDate());
		predicates.add(onStartPredicate);
		predicates.add(onEndPredicate);
		if (criteria.getStatus() != null) {
			Predicate statusPredicate = cb.equal(root.get("status"), criteria.getStatus());
			predicates.add(statusPredicate);
		}
		criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		TypedQuery<UserRequest> query = em.createQuery(criteriaQuery);

		return query.getResultList();
	}
	
}
