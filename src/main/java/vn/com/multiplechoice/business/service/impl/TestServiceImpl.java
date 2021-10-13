package vn.com.multiplechoice.business.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.TestService;
import vn.com.multiplechoice.dao.model.Test;
import vn.com.multiplechoice.dao.repository.TestRepository;

@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test, Long> implements TestService {
    private TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        super(testRepository);
        this.testRepository = testRepository;
    }

}
