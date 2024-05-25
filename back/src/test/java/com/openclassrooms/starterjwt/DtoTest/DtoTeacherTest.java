package com.openclassrooms.starterjwt.DtoTest;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DtoTeacherTest {

    @Test
    void testTeacherDtoConstructorAndGetters() {
        Long id = 1L;
        String lastName = "Doe";
        String firstName = "John";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        TeacherDto teacherDto = new TeacherDto(id, lastName, firstName, createdAt, updatedAt);

        assertEquals(id, teacherDto.getId());
        assertEquals(lastName, teacherDto.getLastName());
        assertEquals(firstName, teacherDto.getFirstName());
        assertEquals(createdAt, teacherDto.getCreatedAt());
        assertEquals(updatedAt, teacherDto.getUpdatedAt());
    }

    @Test
    void testTeacherDtoSetter() {
        TeacherDto teacherDto = new TeacherDto();

        Long id = 1L;
        teacherDto.setId(id);
        assertEquals(id, teacherDto.getId());

        String lastName = "Doe";
        teacherDto.setLastName(lastName);
        assertEquals(lastName, teacherDto.getLastName());

        String firstName = "John";
        teacherDto.setFirstName(firstName);
        assertEquals(firstName, teacherDto.getFirstName());

        LocalDateTime createdAt = LocalDateTime.now();
        teacherDto.setCreatedAt(createdAt);
        assertEquals(createdAt, teacherDto.getCreatedAt());

        LocalDateTime updatedAt = LocalDateTime.now();
        teacherDto.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, teacherDto.getUpdatedAt());
    }



   
}
