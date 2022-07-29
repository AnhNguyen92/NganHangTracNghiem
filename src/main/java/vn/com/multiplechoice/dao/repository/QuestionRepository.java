package vn.com.multiplechoice.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import vn.com.multiplechoice.dao.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

	@Query(value = "select * from questions where user_id = ?1 ", nativeQuery = true)
	List<Question> findByAuthor(Long userId);

    Page<Question> findAllByUserId(Long userId, Pageable pageable);

    @Query(value = "UPDATE questions SET `sttaus` = 'DELETED' ", nativeQuery = true)
    @Modifying
    void deleteQuestion(Long id);

}
