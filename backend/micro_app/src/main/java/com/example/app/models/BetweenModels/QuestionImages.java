package com.example.app.models.BetweenModels;

import com.example.app.models.CompositePK.QuestionImagesPK;
import jakarta.persistence.*;

@Entity
@Table(name = "questionimages")
@IdClass(QuestionImagesPK.class)
public class QuestionImages {
    @Id
    @Column(name = "question_id")
    private Long questionId;

    @Id
    @Column(name = "image_id")
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
