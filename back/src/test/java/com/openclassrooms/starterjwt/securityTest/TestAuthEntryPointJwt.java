package com.openclassrooms.starterjwt.securityTest;


import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.core.type.TypeReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.security.jwt.AuthEntryPointJwt;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAuthEntryPointJwt {

    private AuthEntryPointJwt authEntryPointJwt = new AuthEntryPointJwt();

    @Test
    void commence_ShouldReturnUnauthorizedError() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException authException = new AuthenticationException("Unauthorized") {};

        authEntryPointJwt.commence(request, response, authException);

        // Assert response status code
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());

        // Assert response content type
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());

        // Assert response body
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseBody = mapper.readValue(response.getContentAsString(), new TypeReference<Map<String, Object>>() {});
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, responseBody.get("status"));
        assertEquals("Unauthorized", responseBody.get("error"));
        assertEquals(authException.getMessage(), responseBody.get("message"));
        assertEquals(request.getServletPath(), responseBody.get("path"));
    }
}


