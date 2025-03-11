package com.example.questify.models.SimpleModels;

import java.util.List;

public class AnswerEditRequest {
    private Long id;
    private String text;
    private List<byte[]> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<byte[]> getImages() {
        return images;
    }

    public void setImages(List<byte[]> images) {
        this.images = images;
    }
}
