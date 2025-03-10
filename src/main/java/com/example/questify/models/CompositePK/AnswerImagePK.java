package com.example.questify.models.CompositePK;

import java.io.Serializable;
import java.util.Objects;

public class AnswerImagePK implements Serializable {
    private Long answerId;
    private Long imageId;

    public AnswerImagePK() {}

    public AnswerImagePK(Long answerId, Long imageId) {
        this.answerId = answerId;
        this.imageId = imageId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
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

        if(!(obj instanceof AnswerImagePK answerImagePK)){
            return false;
        }

        return Objects.equals(answerId, answerImagePK.answerId) && Objects.equals(imageId, answerImagePK.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, imageId);
    }
}
