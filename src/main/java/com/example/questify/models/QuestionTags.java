package com.example.questify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table
@IdClass(QuestionTagsPK.class)
public class QuestionTags {
    @Id
    private Long tagId;
    @Id
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
