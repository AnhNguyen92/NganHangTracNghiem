package vn.com.multiplechoice.business.service;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.paging.Page;
import vn.com.multiplechoice.dao.model.paging.PagingRequest;

public interface UserService extends IGenericService<User, Long> {
    User findByUsername(String username);
    public Page<User> searchDataTable(PagingRequest pagingRequest);
}
