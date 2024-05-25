package com.openclassrooms.starterjwt.PayloadTest.ResponseTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;

public class TestJwtResponse {

    // Test constructor and getters
    @Test
    void testConstructorAndGetters() {
        JwtResponse jwtResponse = new JwtResponse("accessToken", 1L, "username", "John", "Doe", true);
        assertEquals("accessToken", jwtResponse.getToken());
        assertEquals("Bearer", jwtResponse.getType());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("username", jwtResponse.getUsername());
        assertEquals("John", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());
    }


    // Test setters
    @Test
    void testSetters() {
        JwtResponse jwtResponse = new JwtResponse("", 0L, "", "", "", false);
        jwtResponse.setToken("accessToken");
        jwtResponse.setType("Bearer");
        jwtResponse.setId(1L);
        jwtResponse.setUsername("username");
        jwtResponse.setFirstName("John");
        jwtResponse.setLastName("Doe");
        jwtResponse.setAdmin(true);
        assertEquals("accessToken", jwtResponse.getToken());
        assertEquals("Bearer", jwtResponse.getType());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("username", jwtResponse.getUsername());
        assertEquals("John", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());
    }
}
