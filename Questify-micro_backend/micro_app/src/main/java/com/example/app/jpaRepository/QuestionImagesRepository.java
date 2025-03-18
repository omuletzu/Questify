package com.example.app.jpaRepository;

import com.example.app.models.BetweenModels.QuestionImages;
import com.example.app.models.CompositePK.QuestionImagesPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionImagesRepository extends JpaRepository<QuestionImages, QuestionImagesPK> {
    public List<QuestionImages> findAllByQuestionId(Long questionId);
    public void deleteAllByQuestionId(Long questionId);
}
