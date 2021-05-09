package vn.com.ids.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.com.ids.multiplechoice.dao.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
