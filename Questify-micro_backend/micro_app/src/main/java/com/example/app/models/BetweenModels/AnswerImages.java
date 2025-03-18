package com.example.app.models.BetweenModels;

import com.example.app.models.CompositePK.AnswerImagePK;
import jakarta.persistence.*;

@Entity
@Table(name = "answerimages")
@IdClass(AnswerImagePK.class)
public class AnswerImages {
    @Id
    @Column(name = "answer_id")
    private Long answerId;
    @Id
    @Column(name = "image_id")
    private Long imageId;

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
