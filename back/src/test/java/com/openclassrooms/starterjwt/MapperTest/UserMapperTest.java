package com.openclassrooms.starterjwt.MapperTest;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    public void testToEntityList() {
        // Given
        List<UserDto> userDtoList = new ArrayList<>();
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setEmail("john.doe@example.com");
        userDto1.setPassword("password");
        userDtoList.add(userDto1);

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setFirstName("Jane");
        userDto2.setLastName("Smith");
        userDto2.setEmail("jane.smith@example.com");
        userDto2.setPassword("password");
        userDtoList.add(userDto2);

        // When
        List<User> userList = userMapper.toEntity(userDtoList);

        // Then
        assertNotNull(userList);
        assertEquals(userDtoList.size(), userList.size());

        // Validate individual mappings
        for (int i = 0; i < userDtoList.size(); i++) {
            UserDto dto = userDtoList.get(i);
            User entity = userList.get(i);
            assertEquals(dto.getId(), entity.getId());
            assertEquals(dto.getFirstName(), entity.getFirstName());
            assertEquals(dto.getLastName(), entity.getLastName());
            assertEquals(dto.getEmail(), entity.getEmail());
        }
    }

    @Test
    public void testToDtoList() {
        // Given
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setEmail("alice.smith@example.com");
        userList.add(user1);

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Bob");
        user2.setLastName("Johnson");
        user2.setEmail("bob.johnson@example.com");
        userList.add(user2);

        // When
        List<UserDto> userDtoList = userMapper.toDto(userList);

        // Then
        assertNotNull(userDtoList);
        assertEquals(userList.size(), userDtoList.size());

        // Validate individual mappings
        for (int i = 0; i < userList.size(); i++) {
            User entity = userList.get(i);
            UserDto dto = userDtoList.get(i);
            assertEquals(entity.getId(), dto.getId());
            assertEquals(entity.getFirstName(), dto.getFirstName());
            assertEquals(entity.getLastName(), dto.getLastName());
            assertEquals(entity.getEmail(), dto.getEmail());
        }
    }
}
