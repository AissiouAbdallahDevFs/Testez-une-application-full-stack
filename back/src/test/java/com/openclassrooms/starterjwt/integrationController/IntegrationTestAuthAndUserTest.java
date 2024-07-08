package com.openclassrooms.starterjwt.integrationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;


@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestAuthAndUserTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    @Test
    void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("yoga@studio.com");
        loginRequest.setPassword("test!1234");
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        assertThat(authentication.isAuthenticated()).isTrue();
    }

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

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"test@example.com\",\"lastName\":\"Doe\",\"firstName\":\"John\",\"password\":\"test123\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void testRegisterUserExistingEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"yoga@studio.com\",\"lastName\":\"Doe\",\"firstName\":\"John\",\"password\":\"test123\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error: Email is already taken!"));
    }

    @Test
    void testAuthenticateUserMissingEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"password\":\"test123\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testAuthenticateUserMissingPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"yoga@studio.com\"}"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testAuthenticateUserIncorrectCredentials() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"yoga@studio.com\",\"password\":\"wrongpassword\"}"))
            .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setEmail("yoga@studio.com");
        user.setPassword(passwordEncoder.encode("test!1234")); 
        userRepository.save(user);
    }
}
