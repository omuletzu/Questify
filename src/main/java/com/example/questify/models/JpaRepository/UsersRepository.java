package com.example.questify.models.JpaRepository;

import com.example.questify.models.SimpleModels.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    public Optional<Users> findUsersByUsername(String username);

    public List<Users> findAllByUsername(String username);
}
