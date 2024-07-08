package com.openclassrooms.starterjwt.securityTest;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtUtilsTest {

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Initialize jwtSecret using reflection (assuming a test configuration sets the value)
        ReflectionTestUtils.setField(jwtUtils, "jwtSecret", "test_secret_key");
    }

    @Test
    public void testGenerateJwtToken() {
        // Mock user principal
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("yoga@studio.com")
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Call method
        String token = jwtUtils.generateJwtToken(authentication);

        // Assert
        assertNotNull(token);

    }
}
