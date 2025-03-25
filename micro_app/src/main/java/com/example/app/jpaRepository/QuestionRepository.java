package com.example.app.jpaRepository;

import com.example.app.models.SimpleModels.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public Page<Question> findAllByOrderByTimestampDesc(Pageable pageable);

    @Query(value = "SELECT * FROM question WHERE title LIKE %:subtitle% ORDER BY timestamp DESC", nativeQuery = true)
    public List<Question> filterBySubtitle(String subtitle);

    public List<Question> findAllById(Long id);

    public List<Question> findAllByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE question SET status = :status WHERE id = :id", nativeQuery = true)
    void setQuestionStatusById(@Param("id") Long questionId, @Param("status") int status);
}
