package com.openclassrooms.starterjwt.DtoTest;

import com.openclassrooms.starterjwt.dto.SessionDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DtoSessionTest {

    @Test
    void testSessionDtoConstructorAndGetters() {
        Long id = 1L;
        String name = "Yoga Session";
        Date date = new Date();
        Long teacherId = 2L;
        String description = "Description of the session.";
        List<Long> users = new ArrayList<>();
        users.add(3L);
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        SessionDto sessionDto = new SessionDto(id, name, date, teacherId, description, users, createdAt, updatedAt);

        assertEquals(id, sessionDto.getId());
        assertEquals(name, sessionDto.getName());
        assertEquals(date, sessionDto.getDate());
        assertEquals(teacherId, sessionDto.getTeacher_id());
        assertEquals(description, sessionDto.getDescription());
        assertEquals(users, sessionDto.getUsers());
        assertEquals(createdAt, sessionDto.getCreatedAt());
        assertEquals(updatedAt, sessionDto.getUpdatedAt());
    }

    @Test
    void testSessionDtoSetter() {
        SessionDto sessionDto = new SessionDto();

        Long id = 1L;
        sessionDto.setId(id);
        assertEquals(id, sessionDto.getId());

        String name = "Yoga Session";
        sessionDto.setName(name);
        assertEquals(name, sessionDto.getName());

        Date date = new Date();
        sessionDto.setDate(date);
        assertEquals(date, sessionDto.getDate());

        Long teacherId = 2L;
        sessionDto.setTeacher_id(teacherId);
        assertEquals(teacherId, sessionDto.getTeacher_id());

        String description = "Description of the session.";
        sessionDto.setDescription(description);
        assertEquals(description, sessionDto.getDescription());

        List<Long> users = new ArrayList<>();
        users.add(3L);
        sessionDto.setUsers(users);
        assertEquals(users, sessionDto.getUsers());

        LocalDateTime createdAt = LocalDateTime.now();
        sessionDto.setCreatedAt(createdAt);
        assertEquals(createdAt, sessionDto.getCreatedAt());

        LocalDateTime updatedAt = LocalDateTime.now();
        sessionDto.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, sessionDto.getUpdatedAt());
    }


  

}
