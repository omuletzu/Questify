package com.example.app.models.Requests;

import org.springframework.web.multipart.MultipartFile;

import javax.print.DocFlavor;
import java.util.List;

public class AnswerEditRequest {
    private Long answerId;
    private String text;
    private List<String> images;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
