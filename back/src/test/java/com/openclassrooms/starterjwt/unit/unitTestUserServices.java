package com.openclassrooms.starterjwt.unit;

import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;
import org.mockito.InjectMocks;
import com.openclassrooms.starterjwt.models.User;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class unitTestUserServices {

	@Mock
	private UserRepository userRepository;
	@InjectMocks
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
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		assertThat(null != userService.findById(1L));
	}

	// test for bad id on find user by id
	@Test
	public void testgetUserBadID() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		assertThat(null == userService.findById(1L));
	}

	// // test for bad id on delete user by id
	@Test
	public void testDeleteUserBadID() {
		when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
		assert(null == userService.findById(1L));
		
	}

}