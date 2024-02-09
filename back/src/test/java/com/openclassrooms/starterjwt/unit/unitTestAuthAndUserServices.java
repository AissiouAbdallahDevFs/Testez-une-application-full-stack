package com.openclassrooms.starterjwt.unit;

import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;
import org.mockito.InjectMocks;
import com.openclassrooms.starterjwt.models.User;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import com.openclassrooms.starterjwt.dto.UserDto;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class unitTestAuthAndUserServices {

	@InjectMocks
	private UserController userController;

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserService userService; 

	@Mock
	private UserMapper userMapper;

    @Mock
    private UserDetailsService userDetailsService;


	@Mock
	private AuthController authController;
	

	// test find user by id
	@Test
	public void getUserByID() {
		User user = new User();
		when(userService.findById(any(Long.class))).thenReturn(user); 
		when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
		ResponseEntity<?>  result = userController.findById("1");
		assert(result.getStatusCodeValue() == 200);
	}

	// test for bad id on find user by id
	@Test
	public void testgetUserBadID() {
		when(userService.findById(any(Long.class))).thenReturn(null); 
		ResponseEntity<?>  result = userController.findById("1");
		System.err.println(result);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	// test for bad id on delete user by id
	@Test
	public void testDeleteUserBadID() {
		when(userService.findById(any(Long.class))).thenReturn(null); 
		ResponseEntity<?>  result = userController.save("1");
		assertThat(result.getStatusCodeValue() == 200);
	}


}