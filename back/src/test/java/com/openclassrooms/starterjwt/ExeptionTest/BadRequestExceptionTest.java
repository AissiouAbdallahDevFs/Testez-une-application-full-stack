package com.openclassrooms.starterjwt.ExeptionTest;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

public class BadRequestExceptionTest {

    @Test
    public void testBadRequestExceptionAnnotation() {
        // Vérifiez que l'exception lève bien une BadRequestException
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException();
        });
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.BAD_REQUEST, responseStatus.value());
    }
}
