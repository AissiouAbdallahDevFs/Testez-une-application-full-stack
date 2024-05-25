package com.openclassrooms.starterjwt.MapperTest;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.openclassrooms.starterjwt.mapper.SessionMapperImpl;

public class SessionMapperTest {

    private final SessionMapper sessionMapper = new SessionMapperImpl();

    @Test
    void testToEntity() {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Yoga Session");
        sessionDto.setDescription("Description");

        // When
        Session session = sessionMapper.toEntity(sessionDto);

        // Then
        assertEquals(sessionDto.getId(), session.getId());
        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getDescription(), session.getDescription());
    }

    @Test
    void testToDto() {
        // Given
        Session session = new Session();
        session.setId(1L);
        session.setName("Yoga Session");
        session.setDescription("Description");

        // When
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Then
        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDescription(), sessionDto.getDescription());
    }


    @Test
    void testToEntityList() {
        // Given
        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setId(1L);
        sessionDto1.setName("Yoga Session 1");
        sessionDto1.setDescription("Description 1");

        SessionDto sessionDto2 = new SessionDto();
        sessionDto2.setId(2L);
        sessionDto2.setName("Yoga Session 2");
        sessionDto2.setDescription("Description 2");

        List<SessionDto> sessionDtos = new ArrayList<>();
        sessionDtos.add(sessionDto1);
        sessionDtos.add(sessionDto2);

        // When
        List<Session> sessions = sessionMapper.toEntity(sessionDtos);

        // Then
        assertEquals(sessionDtos.size(), sessions.size());
    }
}
