package com.example.questify.models.JpaRepository;

import com.example.questify.models.BetweenModels.QuestionTags;
import com.example.questify.models.CompositePK.QuestionTagsPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagsRepository extends JpaRepository<QuestionTags, QuestionTagsPK> {
}
