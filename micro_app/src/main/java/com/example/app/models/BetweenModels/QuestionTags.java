package com.example.app.models.BetweenModels;

import com.example.app.models.CompositePK.QuestionTagsPK;
import jakarta.persistence.*;

@Entity
@Table(name = "questiontags")
@IdClass(QuestionTagsPK.class)
public class QuestionTags {
    @Id
    @Column(name = "tag_id")
    private Long tagId;

    @Id
    @Column(name = "question_id")
    private Long questionId;

    public QuestionTags(Long tagId, Long questionId) {
        this.tagId = tagId;
        this.questionId = questionId;
    }

    public QuestionTags() {

    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
