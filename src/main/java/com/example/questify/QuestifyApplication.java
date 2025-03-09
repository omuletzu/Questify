package com.example.questify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController("/app")
public class QuestifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestifyApplication.class, args);
	}

	@PostMapping("/login")
	public String login(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
		return "";
	}
}
