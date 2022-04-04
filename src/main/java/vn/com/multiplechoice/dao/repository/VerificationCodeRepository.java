package vn.com.multiplechoice.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import vn.com.multiplechoice.dao.model.VerificationCode;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>, JpaSpecificationExecutor<VerificationCode> {
    VerificationCode findByToken(String token);

    boolean existsByToken(String token);
}
