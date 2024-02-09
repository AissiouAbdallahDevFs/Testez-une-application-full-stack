package com.openclassrooms.starterjwt.integration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.controllers.SessionController;
import com.openclassrooms.starterjwt.services.SessionService;
import com.openclassrooms.starterjwt.models.Session;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;




@SpringBootTest
public class testTntegerationTestSession {

	@InjectMocks
	private SessionController sessionController;


	@Mock
	private SessionService sessionService;


	// test find session by id integration
	@Test
	void findById_WithValidId_ReturnsSession()  {
		// Given
		Long sessionId = 1L;
		String id = sessionId.toString();
		Session session = new Session();
		session.setId(sessionId);
		session.setName("Math");
		session.setDescription("Mathematics");

		// When
		when(sessionService.getById(sessionId)).thenReturn(session);

		// Then
		ResponseEntity<?> result = sessionController.findById(id);
		assert(result.getStatusCodeValue() == 200);

	}

	
	
}