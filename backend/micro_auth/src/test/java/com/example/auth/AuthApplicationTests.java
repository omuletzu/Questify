package com.example.auth;

import com.example.auth.controllers.UserController;
import com.example.auth.jpaRepository.UsersRepository;
import com.example.auth.models.Users;
import com.example.auth.services.AuthService;
import com.example.auth.services.UserService;
import com.example.auth.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class AuthApplicationTests {

	@InjectMocks
	private UserService userService;

	@Mock
	private UsersRepository usersRepository;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HttpServletRequest httpServletRequest;

	@InjectMocks
	private UserController userController;

	@Test
	public void testExistingUser() {
		Long id = 1L;
		Boolean isAdmin = false;
		String username = "user";
		String password = "pass";
		String salt = "salt";
		Boolean banned = false;
		String email = "email";
		String phone = "phone";

		Users mockUser = new Users(id, isAdmin, username, password, salt, banned, email, phone);

		when(usersRepository.findById(id)).thenReturn(Optional.of(mockUser));
		Optional<Users> user = userService.getUserById(id);

		assertTrue(user.isPresent());
		assertEquals(username, user.get().getUsername());
	}

	@Test
	public void testNonExistingUser() {
		Long id = 1L;
		when(usersRepository.findById(id)).thenReturn((Optional.empty()));
		Optional<Users> user = userService.getUserById(id);
		assertTrue(user.isEmpty());
	}

	@Test
	public void testFindUsersByUsername() {
		Long id = 1L;
		Boolean isAdmin = false;
		String username = "user";
		String password = "pass";
		String salt = "salt";
		Boolean banned = false;
		String email = "email";
		String phone = "phone";

		Users mockUser = new Users(id, isAdmin, username, password, salt, banned, email, phone);

		when(usersRepository.findUsersByUsername(username)).thenReturn(Optional.of(mockUser));
		Optional<Users> user = userService.getUserByUsername(username);

		assertTrue(user.isPresent());
		assertEquals(username, user.get().getUsername());
	}

	@Test
	public void testBanUser() {
		Long id = 1L;
		boolean banned = true;
		doNothing().when(usersRepository).updateIsActive(id, true);
		userService.banUserById(id, true);
		verify(usersRepository, times(1)).updateIsActive(id, true);
	}

	@Test
	public void removeUser() {
		Long id = 1L;
		doNothing().when(usersRepository).deleteById(id);
		userService.removeUserById(id);
		verify(usersRepository, times(1)).deleteById(id);
	}

	@Test
	public void computeUserScore() {
		Long id = 2L;
		String token = "token";

		when(httpServletRequest.getHeader("Authorization")).thenReturn(token);

		ResponseEntity<Double> scoreByQuestions = ResponseEntity.ok(1.0);
		ResponseEntity<Double> scoreByAnswers = ResponseEntity.ok(2.0);

		String urlQuestionScore = "http://localhost:8080/question/getQuestionScoreById?userId=" + id;
		String urlAnswerScore = "http://localhost:8080/answers/getAnswerScoreById?userId=" + id;

		when(restTemplate.exchange(
				eq(urlQuestionScore),
				eq(HttpMethod.GET),
				any(HttpEntity.class),
				eq(new ParameterizedTypeReference<Double>() {})
		)).thenReturn(scoreByQuestions);

		when(restTemplate.exchange(
				eq(urlAnswerScore),
				eq(HttpMethod.GET),
				any(HttpEntity.class),
				eq(new ParameterizedTypeReference<Double>() {})
		)).thenReturn(scoreByAnswers);

		ResponseEntity<Double> response = userController.computeUserScore(id, httpServletRequest);

		assertEquals(3.0, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(restTemplate, times(1)).exchange(
				eq(urlQuestionScore),
				eq(HttpMethod.GET),
				any(HttpEntity.class),
				eq(new ParameterizedTypeReference<Double>() {})
		);

		verify(restTemplate, times(1)).exchange(
				eq(urlAnswerScore),
				eq(HttpMethod.GET),
				any(HttpEntity.class),
				eq(new ParameterizedTypeReference<Double>() {})
		);
	}
}
