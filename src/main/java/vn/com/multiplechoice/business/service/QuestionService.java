package vn.com.multiplechoice.business.service;

import vn.com.multiplechoice.dao.model.Question;

public interface QuestionService extends IGenericService<Question, Long> {

    Object getPage(int pageNumber, int size);

}
