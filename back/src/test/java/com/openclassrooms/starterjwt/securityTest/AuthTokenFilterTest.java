package com.openclassrooms.starterjwt.securityTest;

import com.openclassrooms.starterjwt.security.jwt.AuthTokenFilter;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = mock(FilterChain.class);
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testDoFilterInternal_ValidJwt() throws Exception {
        // Mock data
        String token = "validToken";
        String username = "user";
        UserDetails userDetails = mock(UserDetails.class);

        // Configure mocks
        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Set the Authorization header
        request.addHeader("Authorization", "Bearer " + token);

        // Use reflection to call the protected method
        Method doFilterInternal = AuthTokenFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        doFilterInternal.setAccessible(true);
        doFilterInternal.invoke(authTokenFilter, request, response, filterChain);

        // Verify interactions
        verify(jwtUtils, times(1)).validateJwtToken(token);
        verify(jwtUtils, times(1)).getUserNameFromJwtToken(token);
        verify(userDetailsService, times(1)).loadUserByUsername(username);

        // Check SecurityContext
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(userDetails, authentication.getPrincipal());
    }

    @Test
    public void testDoFilterInternal_InvalidJwt() throws Exception {
        // Mock data
        String token = "invalidToken";

        // Configure mocks
        when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        // Set the Authorization header
        request.addHeader("Authorization", "Bearer " + token);

        // Use reflection to call the protected method
        Method doFilterInternal = AuthTokenFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        doFilterInternal.setAccessible(true);
        doFilterInternal.invoke(authTokenFilter, request, response, filterChain);

        // Verify interactions
        verify(jwtUtils, times(1)).validateJwtToken(token);
        verify(jwtUtils, never()).getUserNameFromJwtToken(token);
        verify(userDetailsService, never()).loadUserByUsername(anyString());

        // Check SecurityContext
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_NoJwt() throws Exception {
        // Use reflection to call the protected method
        Method doFilterInternal = AuthTokenFilter.class.getDeclaredMethod("doFilterInternal", HttpServletRequest.class, HttpServletResponse.class, FilterChain.class);
        doFilterInternal.setAccessible(true);
        doFilterInternal.invoke(authTokenFilter, request, response, filterChain);

        // Verify interactions
        verify(jwtUtils, never()).validateJwtToken(anyString());
        verify(jwtUtils, never()).getUserNameFromJwtToken(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());

        // Check SecurityContext
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}