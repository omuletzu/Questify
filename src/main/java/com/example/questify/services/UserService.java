package com.example.questify.services;

import com.example.questify.models.JpaRepository.UsersRepository;
import com.example.questify.models.SimpleModels.Users;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findUsersByUsername(username);
    }
}
