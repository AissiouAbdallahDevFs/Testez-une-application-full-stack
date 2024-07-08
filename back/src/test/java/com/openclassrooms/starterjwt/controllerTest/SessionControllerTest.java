package com.openclassrooms.starterjwt.controllerTest;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionControllerTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);
        Session createdSession = sessionService.create(session);
        assertEquals(session, createdSession);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testDelete() {
        Long sessionId = 1L;
        sessionService.delete(sessionId);
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void testFindAll() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session());
        when(sessionRepository.findAll()).thenReturn(sessions);
        List<Session> foundSessions = sessionService.findAll();
        assertEquals(sessions, foundSessions);
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void testGetById_SessionFound() {
        Session session = new Session();
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        Session foundSession = sessionService.getById(1L);
        assertEquals(session, foundSession);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetById_SessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        Session foundSession = sessionService.getById(1L);
        assertNull(foundSession);
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdate() {
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.save(session)).thenReturn(session);
        Session updatedSession = sessionService.update(1L, session);
        assertEquals(session, updatedSession);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testParticipate_UserAndSessionFound() {
        Session session = new Session();
        session.setUsers(new ArrayList<>());
        User user = new User();
        user.setId(1L);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        sessionService.participate(1L, 1L);
        assertTrue(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testParticipate_UserOrSessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
        verify(sessionRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testParticipate_UserAlreadyParticipating() {
        Session session = new Session();
        User user = new User();
        user.setId(1L);
        session.setUsers(new ArrayList<>(List.of(user)));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 1L));
        verify(sessionRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testNoLongerParticipate_UserAndSessionFound() {
        Session session = new Session();
        User user = new User();
        user.setId(1L);
        session.setUsers(new ArrayList<>(List.of(user)));
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        sessionService.noLongerParticipate(1L, 1L);
        assertFalse(session.getUsers().contains(user));
        verify(sessionRepository, times(1)).findById(1L);
        verify(sessionRepository, times(1)).save(session);
    }

    @Test
    public void testNoLongerParticipate_UserOrSessionNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(1L, 1L));
        verify(sessionRepository, times(1)).findById(1L);
    }

    @Test
    public void testNoLongerParticipate_UserNotParticipating() {
        Session session = new Session();
        session.setUsers(new ArrayList<>());
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(1L, 1L));
        verify(sessionRepository, times(1)).findById(1L);
    }

}
