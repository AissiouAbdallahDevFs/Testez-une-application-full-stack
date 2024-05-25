package com.openclassrooms.starterjwt.PayloadTest.RequestTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;

public class TestSignupRequest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    // Test for valid data
    @Test
    void testValidData() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("test123");
        assertTrue(validator.validate(signupRequest).isEmpty());
    }

    // Test for blank email
    @Test
    void testBlankEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("test123");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    // Test for blank first name
    @Test
    void testBlankFirstName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("test123");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    // Test for blank last name
    @Test
    void testBlankLastName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("");
        signupRequest.setPassword("test123");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    // Test for blank password
    @Test
    void testBlankPassword() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    // Test for null email
    @Test
    void testNullEmail() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(null);
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("test123");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    // Test for null first name
    @Test
    void testNullFirstName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName(null);
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("test123");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    // Test for null last name
    @Test
    void testNullLastName() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName(null);
        signupRequest.setPassword("test123");
        assertFalse(validator.validate(signupRequest).isEmpty());
    }

    // Test for null password
    @Test
    void testNullPassword() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword(null);
        assertFalse(validator.validate(signupRequest).isEmpty());
    }
}
