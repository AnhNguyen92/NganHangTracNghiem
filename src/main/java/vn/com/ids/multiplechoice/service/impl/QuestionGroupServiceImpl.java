package vn.com.ids.multiplechoice.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.ids.multiplechoice.dao.model.QuestionGroup;
import vn.com.ids.multiplechoice.dao.repository.QuestionGroupRepository;
import vn.com.ids.multiplechoice.service.AbstractService;
import vn.com.ids.multiplechoice.service.QuestionGroupService;

@Service
@Transactional
public class QuestionGroupServiceImpl extends AbstractService<QuestionGroup, Long> implements QuestionGroupService {
    private QuestionGroupRepository questionGroupRepository;

    @Autowired
    public QuestionGroupServiceImpl(QuestionGroupRepository questionGroupRepository) {
        super(questionGroupRepository);
        this.questionGroupRepository = questionGroupRepository;
    }
}
