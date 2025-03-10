package com.example.questify.models;

public class QuestionImages {
    private int questionId;
    private int imageId;

    public QuestionImages(int questionId, int imageId) {
        this.questionId = questionId;
        this.imageId = imageId;
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
