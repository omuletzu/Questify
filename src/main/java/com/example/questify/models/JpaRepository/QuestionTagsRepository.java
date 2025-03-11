package com.example.questify.models.JpaRepository;

import com.example.questify.models.BetweenModels.QuestionTags;
import com.example.questify.models.CompositePK.QuestionTagsPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionTagsRepository extends JpaRepository<QuestionTags, QuestionTagsPK> {
    public List<QuestionTags> findAllByQuestionId(Long questionId);
    public List<QuestionTags> findAllByTagIdIs(Long tagId);
}
