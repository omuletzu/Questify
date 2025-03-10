package com.example.questify.models;

public class AnswerImages {
    public Long answerId;
    public Long imageId;

    public AnswerImages(Long answerId, Long imageId) {
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
}
