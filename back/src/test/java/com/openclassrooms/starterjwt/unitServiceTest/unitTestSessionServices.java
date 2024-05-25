package com.openclassrooms.starterjwt.unitServiceTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.services.SessionService;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class unitTestSessionServices {
    @InjectMocks
    private SessionController sessionController;

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private SessionDto sessionDto;

    // test for bad id on find session by id service
    @Test
    public void testGetSessionBadID() {
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assert (sessionService.getById(1L) == null);
    }

    // test create session service
    @Test
    public void testCreateSession() {
        when(sessionRepository.save(any(Session.class))).thenReturn(new Session());
        assert (sessionService.create(new Session()) != null);
    }

    // test create session bad service
    @Test
    public void testCreateSessionBad() {
        Session session = new Session();
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        assert (sessionService.create(session).equals(session));
    }

    // test get all sessions
    @Test
    public void testGetAllSessions() {
        Session session = new Session();
        when(sessionRepository.findAll()).thenReturn(java.util.List.of(session));
        assert (sessionService.findAll().size() == 1);
    }

    // test update session
    @Test
    public void testUpdateSession() {
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.save(any(Session.class))).thenReturn(session);
        assert (sessionService.update(1L, session).equals(session));
    }

    // test no longer participate
    @Test
    public void testNoLongerParticipate() {
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assert (sessionService.getById(1L) == null);
    }

   

    // test delete session bad
    @Test
    public void testDeleteSessionBad() {
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        sessionService.delete(1L);
        assert (sessionService.getById(1L) == null);
    }

    // test find session by id
    @Test
    public void testFindSessionById() {
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.of(session));
        assert (sessionService.getById(1L).getId() == 1L);
    }

    // test find session by id bad
    @Test
    public void testFindSessionByIdBad() {
        when(sessionRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assert (sessionService.getById(1L) == null
        );

    }
    
}
