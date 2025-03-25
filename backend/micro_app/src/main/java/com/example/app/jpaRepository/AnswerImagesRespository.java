package com.example.app.jpaRepository;

import com.example.app.models.BetweenModels.AnswerImages;
import com.example.app.models.CompositePK.AnswerImagePK;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerImagesRespository extends JpaRepository<AnswerImages, AnswerImagePK> {
    public List<AnswerImages> findByAnswerId(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM AnswerImages ai WHERE ai.answerId = :id")
    void deleteAnswerImagesByAnswerId(@Param("id") Long id);
}
