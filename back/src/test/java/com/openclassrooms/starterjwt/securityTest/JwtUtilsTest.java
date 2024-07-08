package com.openclassrooms.starterjwt.securityTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;



import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class JwtUtilsTest {

    @Value("${oc.app.jwtSecret}")
    private String jwtSecret;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jwtUtils = new JwtUtils(); // Instantiate JwtUtils manually if necessary
    }

    @Test
    public void testGenerateJwtToken() {
        // Mock user principal
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("yoga@studio.com")
                .password("password")
                .build();

        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Call method
        String token = jwtUtils.generateJwtToken(authentication);
        // Assert
        assertNotNull(token);

        // Verify interactions
        verify(authentication, times(1)).getPrincipal();
    }
}
