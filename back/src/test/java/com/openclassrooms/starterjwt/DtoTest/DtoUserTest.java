package com.openclassrooms.starterjwt.DtoTest;

import com.openclassrooms.starterjwt.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DtoUserTest {

    @Test
    void testUserDtoConstructorAndGetters() {
        Long id = 1L;
        String email = "john.doe@example.com";
        String lastName = "Doe";
        String firstName = "John";
        boolean admin = false;
        String password = "password";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        UserDto userDto = new UserDto(id, email, lastName, firstName, admin, password, createdAt, updatedAt);

        assertEquals(id, userDto.getId());
        assertEquals(email, userDto.getEmail());
        assertEquals(lastName, userDto.getLastName());
        assertEquals(firstName, userDto.getFirstName());
        assertEquals(admin, userDto.isAdmin());
        assertEquals(password, userDto.getPassword());
        assertEquals(createdAt, userDto.getCreatedAt());
        assertEquals(updatedAt, userDto.getUpdatedAt());
    }

    @Test
    void testUserDtoSetter() {
        UserDto userDto = new UserDto();

        Long id = 1L;
        userDto.setId(id);
        assertEquals(id, userDto.getId());

        String email = "john.doe@example.com";
        userDto.setEmail(email);
        assertEquals(email, userDto.getEmail());

        String lastName = "Doe";
        userDto.setLastName(lastName);
        assertEquals(lastName, userDto.getLastName());

        String firstName = "John";
        userDto.setFirstName(firstName);
        assertEquals(firstName, userDto.getFirstName());

        boolean admin = false;
        userDto.setAdmin(admin);
        assertEquals(admin, userDto.isAdmin());

        String password = "password";
        userDto.setPassword(password);
        assertEquals(password, userDto.getPassword());

        LocalDateTime createdAt = LocalDateTime.now();
        userDto.setCreatedAt(createdAt);
        assertEquals(createdAt, userDto.getCreatedAt());

        LocalDateTime updatedAt = LocalDateTime.now();
        userDto.setUpdatedAt(updatedAt);
        assertEquals(updatedAt, userDto.getUpdatedAt());
    }


}
