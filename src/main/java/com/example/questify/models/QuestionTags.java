package com.example.questify.models;

public class QuestionTags {
    private Long tagId;
    private Long questionId;

    public QuestionTags(Long tagId, Long questionId) {
        this.tagId = tagId;
        this.questionId = questionId;
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
