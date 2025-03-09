package com.example.questify;

import com.example.questify.models.AuthController;
import com.example.questify.models.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class QuestifyApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuestifyApplication.class, args);
	}
}
