package com.example.questify.models;

import java.io.Serializable;
import java.util.Objects;

public class QuestionTagsPK implements Serializable {
    private Long questionId;
    private Long tagId;

    public QuestionTagsPK() {}

    public QuestionTagsPK(Long questionId, Long tagId) {
        this.questionId = questionId;
        this.tagId = tagId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof QuestionTagsPK questionTagsPK)){
            return false;
        }

        return Objects.equals(questionId, questionTagsPK.questionId) && Objects.equals(tagId, questionTagsPK.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, tagId);
    }
}
