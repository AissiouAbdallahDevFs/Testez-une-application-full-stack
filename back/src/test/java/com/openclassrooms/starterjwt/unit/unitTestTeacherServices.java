package com.openclassrooms.starterjwt.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;

import lombok.NonNull;


public class unitTestTeacherServices {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;


    // test find teacher by id services
    // @Test
    // public void testGetTeacherByID() {
    //     Teacher teacher = new Teacher();
    //     when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.of(teacher));
    // }
    // test for bad id on find teacher by id
    // @Test
    // public void testGetTeacherBadID() {
    //     when(teacherService.findById(any(Long.class))).thenReturn(null); 
    //     ResponseEntity<?>  result = teacherController.findById("1");
    //     assert(result.getStatusCodeValue() == 404);
    // }    
      
    // // test no Longer Participate in session
    // @Test
    // public void testNoLongerParticipate() {
    //     when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.empty());
    //     assertEquals(null, teacherService.findById((@NonNull Long) 1L));
    // }

}
