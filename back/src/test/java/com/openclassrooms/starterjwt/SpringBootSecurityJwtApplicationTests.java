package com.openclassrooms.starterjwt;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import org.mockito.InjectMocks;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import com.openclassrooms.starterjwt.dto.UserDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.security.core.Authentication;
import java.util.ArrayList;
import org.springframework.security.core.context.SecurityContextHolder;



@SpringBootTest
public class SpringBootSecurityJwtApplicationTests {

	@InjectMocks
	private UserController userController;
	@Mock
	private UserRepository userRepository;
	@Mock
	private UserService userService; 

	@Mock
	private UserMapper userMapper;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtils jwtUtils;

	@Mock
	private AuthenticationManager authenticationManager;




	// ce test test le service de findById recherche par id
	@Test
	public void getUserByID() {
		User user = new User();
		when(userService.findById(any(Long.class))).thenReturn(user); 
		when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
		ResponseEntity<?>  result = userController.findById("1");
		assert(result.getStatusCodeValue() == 200);
	}

	
	@Test
	public void testgetUserByID() {
		when(userService.findById(any(Long.class))).thenReturn(null); 
		ResponseEntity<?>  result = userController.findById("1");
		System.err.println(result);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void login() {
		User user = new User();
		when(userService.findById(any(Long.class))).thenReturn(user); 
		when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
		ResponseEntity<?>  result = userController.findById("1");
		assert(result.getStatusCodeValue() == 200);
	}

	@Test
    void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("token");
        ResponseEntity<?> result = userController.authenticateUser(loginRequest);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isInstanceOf(JwtResponse.class);
        assertThat(((JwtResponse) result.getBody()).getToken()).isEqualTo("token");
    }

	
}