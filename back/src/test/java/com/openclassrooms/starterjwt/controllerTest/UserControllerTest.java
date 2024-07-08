package com.openclassrooms.starterjwt.controllerTest;

import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.Optional;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById_UserFound() {
        // Mock data
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        when(userService.findById(1L)).thenReturn(user);

        // Mock mapping
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Call controller method
        ResponseEntity<?> response = userController.findById("1");

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify response body
        UserDto responseBody = (UserDto) response.getBody();
        assertEquals(user.getId(), responseBody.getId());
        assertEquals(user.getFirstName(), responseBody.getFirstName());
        assertEquals(user.getLastName(), responseBody.getLastName());

        // Verify service method invocation
        verify(userService, times(1)).findById(1L);
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    public void testSave_UserDeletedSuccessfully() {
        // Mock data
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userService.findById(1L)).thenReturn(user);

        // Mock UserDetails
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Call controller method
        ResponseEntity<?> response = userController.save("1");

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify service method invocation
        verify(userService, times(1)).findById(1L);
        verify(userService, times(1)).delete(1L);
    }

}
