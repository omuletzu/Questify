package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM question ORDER BY timestamp DESC LIMIT :limit", nativeQuery = true)
    public List<Question> getRecentNQuestions(@Param("limit") int limit);
}
