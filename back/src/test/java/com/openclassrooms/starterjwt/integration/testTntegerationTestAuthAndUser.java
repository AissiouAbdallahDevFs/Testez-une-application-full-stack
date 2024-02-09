package com.openclassrooms.starterjwt.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import org.mockito.InjectMocks;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
public class testTntegerationTestAuthAndUser {

	@InjectMocks
	private UserController userController;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	

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