package com.openclassrooms.starterjwt.integration;

import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TeacherControllerIntegrationTest {

    @Autowired
    private TeacherController teacherController;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherMapper teacherMapper;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Create a new teacher and set its fields
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
    }

    // Test find teacher by id integration
    @Test
    void findById_WithValidId_ReturnsTeacherDto() {
        // Given
        Long teacherId = teacher.getId();
        String id = teacherId.toString();

        // Mock the service and mapper behavior
        when(teacherService.findById(teacherId)).thenReturn(teacher);

        // Create a TeacherDto
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacherId);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);

        // When
        ResponseEntity<?> result = teacherController.findById(id);

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isInstanceOf(TeacherDto.class);
        TeacherDto returnedTeacherDto = (TeacherDto) result.getBody();
        assertThat(returnedTeacherDto.getId()).isEqualTo(teacherId);
        assertThat(returnedTeacherDto.getFirstName()).isEqualTo("John");
        assertThat(returnedTeacherDto.getLastName()).isEqualTo("Doe");
    }

    // Test find all teachers integration
    @Test
    void findAll_ReturnsListOfTeacherDto() {
        // Mock the service and mapper behavior
        when(teacherService.findAll()).thenReturn(java.util.List.of(teacher));

        // Create a TeacherDto
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");
        when(teacherMapper.toDto(java.util.List.of(teacher))).thenReturn(java.util.List.of(teacherDto));

        // When
        ResponseEntity<?> result = teacherController.findAll();

        // Then
        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isInstanceOf(java.util.List.class);
        java.util.List<TeacherDto> returnedTeacherDtos = (java.util.List<TeacherDto>) result.getBody();
        assertThat(returnedTeacherDtos.size()).isEqualTo(1);
        assertThat(returnedTeacherDtos.get(0).getId()).isEqualTo(1L);
        assertThat(returnedTeacherDtos.get(0).getFirstName()).isEqualTo("John");
        assertThat(returnedTeacherDtos.get(0).getLastName()).isEqualTo("Doe");
    }
}
