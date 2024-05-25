package com.openclassrooms.starterjwt.MapperTest;
import com.openclassrooms.starterjwt.mapper.TeacherMapperImpl; 
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherMapperTest {


    private final TeacherMapper teacherMapper = new TeacherMapperImpl();

    @Test
    void testToEntity() {
        // Given
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName("Doe");
        teacherDto.setFirstName("John");

        // When
        Teacher teacher = teacherMapper.toEntity(teacherDto);

        // Then
        assertEquals(teacherDto.getId(), teacher.getId());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
    }

}