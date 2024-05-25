package com.openclassrooms.starterjwt.mapper;

import java.util.List;

import com.openclassrooms.starterjwt.dto.TeacherDto;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(TeacherDto teacherDto);

    List<E> toEntity(List<D> dtoList);

    List<D> toDto(TeacherDto teacherDto);
}
