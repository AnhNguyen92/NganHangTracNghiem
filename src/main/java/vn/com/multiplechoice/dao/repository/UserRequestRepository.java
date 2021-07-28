package vn.com.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.multiplechoice.dao.model.UserRequest;

public interface UserRequestRepository extends JpaRepository<UserRequest, Long>, JpaSpecificationExecutor<UserRequest> {

}
