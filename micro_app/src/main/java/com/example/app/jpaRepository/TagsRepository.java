package com.example.app.jpaRepository;

import com.example.app.models.SimpleModels.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagsRepository extends JpaRepository<Tags, Long> {
    public Optional<Tags> findByTagName(String tag);
}
