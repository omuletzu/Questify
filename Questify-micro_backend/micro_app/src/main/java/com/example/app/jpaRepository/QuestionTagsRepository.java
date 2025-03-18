package com.example.app.jpaRepository;

import com.example.app.models.BetweenModels.QuestionTags;
import com.example.app.models.CompositePK.QuestionTagsPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionTagsRepository extends JpaRepository<QuestionTags, QuestionTagsPK> {
    public List<QuestionTags> findAllByQuestionId(Long questionId);
    public List<QuestionTags> findAllByTagIdIs(Long tagId);
    public void deleteAllByQuestionId(Long questionId);
}
