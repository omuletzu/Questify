package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    public Page<Question> findAllByOrderByTimestampDesc(Pageable pageable);

    @Query(value = "SELECT * FROM question WHERE title LIKE %:subtitle%", nativeQuery = true)
    public List<Question> filterBySubtitle(String subtitle);

    public List<Question> findAllById(Long id);

    public List<Question> findAllByUserId(Long userId);
}
