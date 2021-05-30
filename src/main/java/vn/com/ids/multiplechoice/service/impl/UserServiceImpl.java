package vn.com.ids.multiplechoice.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.ids.multiplechoice.business.service.AbstractService;
import vn.com.ids.multiplechoice.business.service.UserService;
import vn.com.ids.multiplechoice.dao.model.User;
import vn.com.ids.multiplechoice.dao.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl  extends AbstractService<User, Long> implements UserService {
    private UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }
    
}
