package com.example.app.models.CompositePK;

import java.io.Serializable;
import java.util.Objects;

public class QuestionImagesPK implements Serializable {
    private Long questionId;
    private Long imageId;

    public QuestionImagesPK() {}

    public QuestionImagesPK(Long questionId, Long imageId) {
        this.questionId = questionId;
        this.imageId = imageId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }

        if(!(obj instanceof QuestionImagesPK questionImagesPK)){
            return false;
        }

        return Objects.equals(questionId, questionImagesPK.questionId) && Objects.equals(imageId, questionImagesPK.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, imageId);
    }
}
