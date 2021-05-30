package vn.com.multiplechoice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.repository.QuestionRepository;

public class QuestionServiceImpl extends AbstractService<Question, Long> implements QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository = questionRepository;
    }
    
}
