package com.openclassrooms.starterjwt.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import org.mockito.InjectMocks;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;



@SpringBootTest
public class testTntegerationTestAuthAndUser {

	@InjectMocks
	private UserController userController;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtils jwtUtils;

	 @BeforeEach
    void setUp() {
        // Clean the database before each test
        userRepository.deleteAll();

        // Create a new user with the credentials you want to test
        User user = new User();
        user.setEmail("yoga@studio.com");
        user.setPassword(passwordEncoder.encode("test!1234")); // Encode the password
        userRepository.save(user);
    }

	// test  good credentials on login
	@Test
    void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		assertThat(authentication.isAuthenticated()).isTrue();
	}
	// test generate jwt token
	@Test
	void testGenerateJwtToken() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		String jwt = jwtUtils.generateJwtToken(authentication);
		assertThat(jwt).isNotNull();
	}
	// test bad credentials on login
	@Test
    void testBadCredentials() {
        LoginRequest loginRequest = new LoginRequest();
		// bad credentials for test
        loginRequest.setEmail("yogao@studio.com");
        loginRequest.setPassword("test!1234");
		Throwable exception = assertThrows(BadCredentialsException.class, () -> {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		});
		assertEquals("Bad credentials", exception.getMessage());
    }
}