package com.example.questify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table
@IdClass(AnswerImagePK.class)
public class AnswerImages {
    @Id
    public Long answerId;
    @Id
    public Long imageId;

    public AnswerImages(Long answerId, Long imageId) {
        this.answerId = answerId;
        this.imageId = imageId;
    }

    public AnswerImages() {

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
}
