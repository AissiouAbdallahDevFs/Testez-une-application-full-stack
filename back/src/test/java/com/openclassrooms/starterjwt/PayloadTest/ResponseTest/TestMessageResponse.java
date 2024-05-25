package com.openclassrooms.starterjwt.PayloadTest.ResponseTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;

public class TestMessageResponse {

    // Test constructor and getter
    @Test
    void testConstructorAndGetters() {
        MessageResponse messageResponse = new MessageResponse("Test message");
        assertEquals("Test message", messageResponse.getMessage());
    }

    // Test setter
    @Test
    void testSetter() {
        MessageResponse messageResponse = new MessageResponse("");
        messageResponse.setMessage("New message");
        assertEquals("New message", messageResponse.getMessage());
    }


}
