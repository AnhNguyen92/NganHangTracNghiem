package vn.com.multiplechoice.business.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.ExamResultService;
import vn.com.multiplechoice.dao.model.ExamResult;
import vn.com.multiplechoice.dao.repository.ExamResultRepository;

@Service
@Transactional
public class ExamResultIServiceImpl extends AbstractService<ExamResult
, Long> implements ExamResultService {
	private ExamResultRepository repository;

	@Autowired
	public ExamResultIServiceImpl(ExamResultRepository repository) {
		super(repository);
		this.repository = repository;
	}

}
