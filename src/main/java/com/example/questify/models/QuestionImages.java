package com.example.questify.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table
@IdClass(QuestionImagesPK.class)
public class QuestionImages {
    @Id
    private int questionId;
    @Id
    private int imageId;

    public QuestionImages(int questionId, int imageId) {
        this.questionId = questionId;
        this.imageId = imageId;
    }

    public QuestionImages() {

    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
