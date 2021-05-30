package vn.com.ids.multiplechoice.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.ids.multiplechoice.business.service.AbstractService;
import vn.com.ids.multiplechoice.business.service.TestFeedbackService;
import vn.com.ids.multiplechoice.dao.model.TestFeedback;
import vn.com.ids.multiplechoice.dao.repository.TestFeedbackRepository;

@Service
@Transactional
public class TestFeedbackServiceImpl extends AbstractService<TestFeedback, Long> implements TestFeedbackService {
    private TestFeedbackRepository feedbackRepository;
    
    @Autowired
    public TestFeedbackServiceImpl(TestFeedbackRepository feedbackRepository) {
        super(feedbackRepository);
        this.feedbackRepository = feedbackRepository;
    }
}
