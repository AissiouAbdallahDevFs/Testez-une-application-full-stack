package com.openclassrooms.starterjwt.unitServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;


@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class unitTestTeacherServices {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private Teacher teacher;


    // test find teacher by id services
    @Test
    public void testGetTeacherByID() {
        Teacher teacher = new Teacher();
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.of(teacher));
        assert (teacherService.findById(1L) != null);
    }

    // test find teacher by bad id services
    @Test
    public void testGetTeacherByIDBad() {
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assert (teacherService.findById(1L) == null);
    }

    // test find all teachers services
    @Test
    public void testGetAllTeachers() {
        Teacher teacher = new Teacher();
        when(teacherRepository.findAll()).thenReturn(java.util.List.of(teacher));
        assert (teacherService.findAll() != null);
    }









}
