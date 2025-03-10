package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {}
