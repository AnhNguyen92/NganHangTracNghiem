package vn.com.ids.multiplechoice.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.ids.multiplechoice.dao.model.TestQuestion;
import vn.com.ids.multiplechoice.dao.repository.TestQuestionRepository;
import vn.com.ids.multiplechoice.service.AbstractService;
import vn.com.ids.multiplechoice.service.TestQuestionService;

@Service
@Transactional
public class TestQuestionServiceImpl extends AbstractService<TestQuestion, Long> implements TestQuestionService {
    private TestQuestionRepository testQuestionRepository;

    @Autowired
    public TestQuestionServiceImpl(TestQuestionRepository testQuestionRepository) {
        super(testQuestionRepository);
        this.testQuestionRepository = testQuestionRepository;
    }

}
