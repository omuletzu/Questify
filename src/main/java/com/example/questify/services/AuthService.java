package com.example.questify.services;

import com.example.questify.models.SimpleModels.Users;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthService {
    public String[] getHashedPasswordAndSalt(String password) {
        try{
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);

            byte[] hashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            String saltString = Base64.getEncoder().encodeToString(salt);
            String hashedString = Base64.getEncoder().encodeToString(hashedPassword);

            return new String[] {hashedString, saltString};
        }
        catch (java.security.NoSuchAlgorithmException e) {
            return null;
        }
    }

    public boolean verifyPassword(String password, String storedPassword, String storedSalt) {
        try{
            byte[] salt = Base64.getDecoder().decode(storedSalt);

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);

            byte[] newHashedPassword = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            String newHashedPasswordString = Base64.getEncoder().encodeToString(newHashedPassword);

            return storedPassword.equals(newHashedPasswordString);
        }
        catch (java.security.NoSuchAlgorithmException e) {
            return false;
        }
    }
}
