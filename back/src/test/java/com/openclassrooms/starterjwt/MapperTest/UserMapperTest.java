package com.openclassrooms.starterjwt.MapperTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.mapper.UserMapperImpl;


public class UserMapperTest {
    private final UserMapper userMapper = new UserMapperImpl();
    @Test
    public void toEntityTest() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("JohnDoe");
        userDto.setPassword("password");
        userDto.setEmail("test@test.fr");
        userDto.setLastName("Doe");

        // When
        User user = userMapper.toEntity(userDto);

        // Then
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getPassword(), user.getPassword());

    }

 
}