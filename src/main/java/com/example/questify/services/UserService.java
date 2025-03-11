package com.example.questify.services;

import com.example.questify.models.JpaRepository.UsersRepository;
import com.example.questify.models.SimpleModels.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findUsersByUsername(username);
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public boolean createNewUser(Users user) {
        try{
            usersRepository.save(user);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean removeUserById(Long id) {
        try{
            usersRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public Optional<Users> getUsersByUsername(String username) {
        return usersRepository.findUsersByUsername(username);
    }

    public List<Users> findAllUsersByUsername(String username) {
        return usersRepository.findAllByUsername(username);
    }

    public void banUserById(Long userId, boolean banned) {
        usersRepository.updateIsActive(userId, banned);
    }
}
