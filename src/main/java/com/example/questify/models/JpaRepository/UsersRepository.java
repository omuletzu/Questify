package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findUsersByUsername(String username);
}
