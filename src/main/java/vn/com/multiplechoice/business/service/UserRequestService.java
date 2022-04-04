package vn.com.multiplechoice.business.service;

import java.util.List;

import vn.com.multiplechoice.dao.criteria.UserRequestCriteria;
import vn.com.multiplechoice.dao.model.UserRequest;

public interface UserRequestService extends IGenericService<UserRequest, Long> {

	List<UserRequest> findAll(UserRequestCriteria criteria);
}
