package com.openclassrooms.starterjwt.integrationController;

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

import static org.mockito.ArgumentMatchers.any;
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
    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a new session
        session = new Session();
        session.setName("Yoga Session");
        session.setId(1L);

        // Initialize sessionDto
        sessionDto = new SessionDto();
        sessionDto.setName("Yoga Session");

        // Mock the behavior of the service and mapper
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(session);
    }

    @AfterEach
    void tearDown() {
        if (session != null) {
            sessionService.delete(session.getId());
        }
    }

    @Test
    void findById_WithValidId_ReturnsSessionDto() {
        Long sessionId = session.getId();
        String id = sessionId.toString();

        when(sessionService.getById(sessionId)).thenReturn(session);
        sessionDto.setId(sessionId);
        sessionDto.setName("Yoga Session");
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> result = sessionController.findById(id);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isInstanceOf(SessionDto.class);
        SessionDto returnedSessionDto = (SessionDto) result.getBody();
        assertThat(returnedSessionDto.getId()).isEqualTo(sessionId);
        assertThat(returnedSessionDto.getName()).isEqualTo("Yoga Session");
    }

    @Test
    void create_WithValidSession_ReturnsSessionDto() {
        sessionDto.setName("Yoga Session 2");

        Session newSession = new Session();
        newSession.setName("Yoga Session 2");
        when(sessionService.create(any(Session.class))).thenReturn(newSession);

        SessionDto createdSessionDto = new SessionDto();
        createdSessionDto.setName("Yoga Session 2");
        when(sessionMapper.toDto(newSession)).thenReturn(createdSessionDto);
        when(sessionMapper.toEntity(any(SessionDto.class))).thenReturn(newSession);

        ResponseEntity<?> result = sessionController.create(sessionDto);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isInstanceOf(SessionDto.class);
        SessionDto returnedSessionDto = (SessionDto) result.getBody();
        assertThat(returnedSessionDto.getName()).isEqualTo("Yoga Session 2");
    }

    @Test
    void update_WithValidSession_ReturnsSessionDto() {
        Long sessionId = session.getId();
        String id = sessionId.toString();
        sessionDto.setName("Yoga Session 2");

        when(sessionService.update(sessionId, session)).thenReturn(session);
        sessionDto.setId(sessionId);
        sessionDto.setName("Yoga Session 2");
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> result = sessionController.update(id, sessionDto);

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isInstanceOf(SessionDto.class);
        SessionDto returnedSessionDto = (SessionDto) result.getBody();
        assertThat(returnedSessionDto.getId()).isEqualTo(sessionId);
        assertThat(returnedSessionDto.getName()).isEqualTo("Yoga Session 2");
    }


 
}
