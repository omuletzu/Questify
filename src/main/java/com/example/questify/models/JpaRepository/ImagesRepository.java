package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {}
