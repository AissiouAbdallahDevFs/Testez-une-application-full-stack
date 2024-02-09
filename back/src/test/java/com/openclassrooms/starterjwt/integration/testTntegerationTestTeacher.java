package com.openclassrooms.starterjwt.integration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;




@SpringBootTest
public class testTntegerationTestTeacher {

	@InjectMocks
	private TeacherController teacherController;


	@Mock
	private TeacherService teacherService;


	// test find teacher by id integration
	@Test
	void findById_WithValidId_ReturnsTeacher()  {
		// Given
		Long teacherId = 1L;
		String id = teacherId.toString();
		Teacher teacher = new Teacher();
		teacher.setId(teacherId);
		teacher.setFirstName("John");
		teacher.setLastName("Doe");

		// When
		when(teacherService.findById(teacherId)).thenReturn(teacher);

		// Then
		ResponseEntity<?> result = teacherController.findById(id);
		assert(result.getStatusCodeValue() == 200);

	}
	
}