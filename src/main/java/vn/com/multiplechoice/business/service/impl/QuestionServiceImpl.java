package vn.com.multiplechoice.business.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.QuestionService;
import vn.com.multiplechoice.dao.model.Question;
import vn.com.multiplechoice.dao.repository.QuestionRepository;

@Service
@Transactional
public class QuestionServiceImpl extends AbstractService<Question, Long> implements QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository = questionRepository;
    }

	@Override
	public Object getPage(int pageNumber, int size) {
		return null;
	}

	@Override
	public List<Question> findByAuthor(Long userId) {
		return questionRepository.findByAuthor(userId);
	}
    
}
