package vn.com.multiplechoice.business.service;

import vn.com.multiplechoice.dao.model.User;
import vn.com.multiplechoice.dao.model.paging.Page;
import vn.com.multiplechoice.dao.model.paging.PagingRequest;

public interface UserService extends IGenericService<User, Long> {
    public User findByUsername(String username);

    public User findByEmail(String email);

    public Page<User> searchDataTable(PagingRequest pagingRequest);
    
    public void resetNewPassword(User user, String password);
    
}
