package com.openclassrooms.starterjwt.PayloadTest.RequestTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;

public class TestLoginRequest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    // Test for valid email and password
    @Test
    void testValidEmailAndPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("test123");
        assertTrue(validator.validate(loginRequest).isEmpty());
    }

    // Test for blank email
    @Test
    void testBlankEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");
        loginRequest.setPassword("test123");
        assertFalse(validator.validate(loginRequest).isEmpty());
    }

    // Test for blank password
    @Test
    void testBlankPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("");
        assertFalse(validator.validate(loginRequest).isEmpty());
    }

    // Test for null email
    @Test
    void testNullEmail() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);
        loginRequest.setPassword("test123");
        assertFalse(validator.validate(loginRequest).isEmpty());
    }

    // Test for null password
    @Test
    void testNullPassword() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword(null);
        assertFalse(validator.validate(loginRequest).isEmpty());
    }
}
