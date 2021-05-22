package vn.com.ids.multiplechoice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import vn.com.ids.multiplechoice.dao.model.Question;
import vn.com.ids.multiplechoice.dao.repository.QuestionRepository;
import vn.com.ids.multiplechoice.service.AbstractService;
import vn.com.ids.multiplechoice.service.QuestionService;

public class QuestionServiceImpl extends AbstractService<Question, Long> implements QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository = questionRepository;
    }
    
}
