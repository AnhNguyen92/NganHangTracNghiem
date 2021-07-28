package vn.com.multiplechoice.business.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.multiplechoice.business.service.AbstractService;
import vn.com.multiplechoice.business.service.UserRequestService;
import vn.com.multiplechoice.dao.model.UserRequest;
import vn.com.multiplechoice.dao.repository.UserRequestRepository;

@Service
@Transactional
public class UserRequestServiceImpl extends AbstractService<UserRequest, Long> implements UserRequestService {
    private UserRequestRepository userRequestRepository;

    @Autowired
    public UserRequestServiceImpl(UserRequestRepository userRequestRepository) {
        super(userRequestRepository);
        this.userRequestRepository = userRequestRepository;
    }
}
