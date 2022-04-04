package vn.com.multiplechoice.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import vn.com.multiplechoice.dao.model.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	@Query(value = " Select * from users where username = ?1 ", nativeQuery = true)
	public User findByUsername(String username);

    public User findByEmail(String email);

    @Query(value = " Select * from users u where u.status = 'Chưa kích hoạt'; ", nativeQuery = true)
    public List<User> getWaitingUsers();
}
