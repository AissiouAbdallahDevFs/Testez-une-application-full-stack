package com.openclassrooms.starterjwt;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.services.UserService;
import org.mockito.InjectMocks;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import com.openclassrooms.starterjwt.dto.UserDto;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.security.core.Authentication;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



@SpringBootTest
public class unitTestAuthAndUserServices {

	@InjectMocks
	private UserController userController;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtUtils;

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserService userService; 

	@Mock
	private UserMapper userMapper;

    @Mock
    private UserDetailsService userDetailsService;


	@Mock
	private AuthController authController;

	// test find user by id
	@Test
	public void getUserByID() {
		User user = new User();
		when(userService.findById(any(Long.class))).thenReturn(user); 
		when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
		ResponseEntity<?>  result = userController.findById("1");
		assert(result.getStatusCodeValue() == 200);
	}

	// test for bad id on find user by id
	@Test
	public void testgetUserBadID() {
		when(userService.findById(any(Long.class))).thenReturn(null); 
		ResponseEntity<?>  result = userController.findById("1");
		System.err.println(result);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	// test bad credentials on login
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
        loginRequest.setEmail("yogoa@studio.com");
        loginRequest.setPassword("test!1234");
		Throwable exception = assertThrows(BadCredentialsException.class, () -> {
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		});
		assertEquals("Bad credentials", exception.getMessage());
    }
}