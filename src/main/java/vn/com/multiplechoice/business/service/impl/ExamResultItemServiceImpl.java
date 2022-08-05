package vn.com.multiplechoice.business.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.ExamResultItemService;
import vn.com.multiplechoice.dao.model.ExamResultItem;
import vn.com.multiplechoice.dao.repository.ExamResultItemRepository;

@Service
@Transactional
public class ExamResultItemServiceImpl extends AbstractService<ExamResultItem, Long> implements ExamResultItemService {
	private ExamResultItemRepository repository;

	@Autowired
	public ExamResultItemServiceImpl(ExamResultItemRepository repository) {
		super(repository);
		this.repository = repository;
	}

}
