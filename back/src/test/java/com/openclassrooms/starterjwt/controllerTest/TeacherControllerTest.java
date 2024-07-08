package com.openclassrooms.starterjwt.controllerTest;

import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById_TeacherFound() {
        // Mock data
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherMapper.toDto(any(Teacher.class))).thenReturn(teacherDto);

        // Call controller method
        ResponseEntity<?> response = teacherController.findById("1");

        // Verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(teacherDto, response.getBody());

        // Verify service method invocation
        verify(teacherService, times(1)).findById(1L);
        when(teacherMapper.toDto(any(Teacher.class))).thenReturn(teacherDto);
    }

    @Test
    public void testFindById_TeacherNotFound() {
        // Mock data
        when(teacherService.findById(999L)).thenReturn(null);

        // Call controller method
        ResponseEntity<?> response = teacherController.findById("999");

        // Verify response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify service method invocation
        verify(teacherService, times(1)).findById(999L);
        verify(teacherMapper, never()).toDto(any(Teacher.class));

    }

    @Test
    public void testFindById_InvalidIdFormat() {
        // Call controller method with invalid ID format
        ResponseEntity<?> response = teacherController.findById("abc");

        // Verify response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verify service method invocation
        verify(teacherService, never()).findById(anyLong());
        verify(teacherMapper, never()).toDto(any(Teacher.class));

    }
}
