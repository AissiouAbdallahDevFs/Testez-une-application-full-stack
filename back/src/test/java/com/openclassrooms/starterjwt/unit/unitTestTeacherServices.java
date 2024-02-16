package com.openclassrooms.starterjwt.unit;

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



}
