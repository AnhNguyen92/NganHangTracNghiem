package vn.com.multiplechoice.business.service;

import vn.com.multiplechoice.dao.model.User;

public interface UserService extends IGenericService<User, Long> {
    User findByUsername(String username);
}
