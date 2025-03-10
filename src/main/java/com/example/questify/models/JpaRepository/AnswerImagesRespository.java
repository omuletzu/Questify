package com.example.questify.models.JpaRepository;

import com.example.questify.models.BetweenModels.AnswerImages;
import com.example.questify.models.CompositePK.AnswerImagePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerImagesRespository extends JpaRepository<AnswerImages, AnswerImagePK> {
    public List<AnswerImages> findByAnswerId(Long id);

    public void deleteAnswerImagesByAnswerId(Long id);
}
