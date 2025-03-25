package com.example.app.jpaRepository;

import com.example.app.models.SimpleModels.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {}
