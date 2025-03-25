package com.example.app.jpaRepository;

import com.example.app.models.SimpleModels.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    public List<Answer> findAnswerByQuestionId(Long id);

    public List<Answer> findAllByUserId(Long userId);
}
