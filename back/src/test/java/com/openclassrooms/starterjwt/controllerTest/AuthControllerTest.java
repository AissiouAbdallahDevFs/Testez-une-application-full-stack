package com.openclassrooms.starterjwt.controllerTest;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAuthenticateUser() {
        // Mock login request
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        // Mock authentication
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);

        // Mock user retrieval from repository
        User user = new User("test@example.com", "User", "Test", "hashedPassword", false);
        when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(user));

        // Mock UserDetailsImpl returned by authentication.getPrincipal()
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal())
                .thenReturn(userDetails);
        when(userDetails.getId()).thenReturn(1L);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(userDetails.getFirstName()).thenReturn("Test");
        when(userDetails.getLastName()).thenReturn("User");
        when(userDetails.getAdmin()).thenReturn(false);

        // Mock JWT token generation
        when(jwtUtils.generateJwtToken(authentication))
                .thenReturn("mock.jwt.token");

        // Perform controller method call
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertEquals("mock.jwt.token", jwtResponse.getToken());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("test@example.com", jwtResponse.getUsername());
        assertEquals("Test", jwtResponse.getFirstName());
        assertEquals("User", jwtResponse.getLastName());
        assertEquals(false, jwtResponse.getAdmin());

        // Verify interactions
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(jwtUtils, times(1)).generateJwtToken(authentication);
        verify(userDetails, times(1)).getId();
        verify(userDetails, times(1)).getFirstName();
        verify(userDetails, times(1)).getLastName();
    }
        


   
}