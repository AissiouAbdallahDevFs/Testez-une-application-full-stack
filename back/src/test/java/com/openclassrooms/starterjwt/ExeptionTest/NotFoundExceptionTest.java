package com.openclassrooms.starterjwt.ExeptionTest;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.jupiter.api.Assertions.*;

public class NotFoundExceptionTest {

    @Test
    public void testNotFoundExceptionAnnotation() {
        // Vérifiez que l'exception lève bien une NotFoundException
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException();
        });

        // Vérifiez l'annotation ResponseStatus
        ResponseStatus responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        assertNotNull(responseStatus);
        assertEquals(HttpStatus.NOT_FOUND, responseStatus.value());
    }
}