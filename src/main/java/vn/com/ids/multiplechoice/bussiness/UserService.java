package vn.com.ids.multiplechoice.bussiness;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.ids.multiplechoice.dao.model.User;
import vn.com.ids.multiplechoice.dao.repository.UserRepository;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public List<User> listAll() {
        return userRepository.findAll();
    }
     
    public void save(User user) {
        userRepository.save(user);
    }
     
    public Optional<User> get(long id) {
        return userRepository.findById(id);
    }
     
    public void delete(long id) {
        userRepository.deleteById(id);
    }
    
}
