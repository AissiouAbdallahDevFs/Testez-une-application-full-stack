package com.openclassrooms.starterjwt.integration;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SessionControllerIntegrationTest {

    @Autowired
    private SessionController sessionController;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionMapper sessionMapper;

    private Session session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a new session and save it
        session = new Session();
        session.setName("Yoga Session"); // Set other necessary fields
        session.setId(1L); // Set the ID if necessary
        when(sessionService.create(session)).thenReturn(session);
        session = sessionService.create(session); // Mocked creation
    }

    @AfterEach
    void tearDown() {
        // Delete the session after each test
        sessionService.delete(session.getId());
    }

    // Test find session by id integration
    @SuppressWarnings("null")
    @Test
    void findById_WithValidId_ReturnsSessionDto() {
        // Given
        Long sessionId = session.getId();
        String id = sessionId.toString();

        // Mock the service and mapper behavior
        when(sessionService.getById(sessionId)).thenReturn(session);

        // Create a SessionDto
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(sessionId);
        sessionDto.setName("Yoga Session");
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // When
        ResponseEntity<?> result = sessionController.findById(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isInstanceOf(SessionDto.class);
        SessionDto returnedSessionDto = (SessionDto) result.getBody();
        assertThat(returnedSessionDto.getId()).isEqualTo(sessionId);
        assertThat(returnedSessionDto.getName()).isEqualTo("Yoga Session");
    }
}
