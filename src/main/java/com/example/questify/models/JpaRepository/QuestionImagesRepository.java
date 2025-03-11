package com.example.questify.models.JpaRepository;

import com.example.questify.models.BetweenModels.QuestionImages;
import com.example.questify.models.CompositePK.QuestionImagesPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionImagesRepository extends JpaRepository<QuestionImages, QuestionImagesPK> {
}
