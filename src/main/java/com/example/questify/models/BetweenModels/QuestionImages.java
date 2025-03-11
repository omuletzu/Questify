package com.example.questify.models.BetweenModels;

import com.example.questify.models.CompositePK.QuestionImagesPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table
@IdClass(QuestionImagesPK.class)
public class QuestionImages {
    @Id
    private Long questionId;
    @Id
    private Long imageId;

    public QuestionImages(Long questionId, Long imageId) {
        this.questionId = questionId;
        this.imageId = imageId;
    }

    public QuestionImages() {

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
}
