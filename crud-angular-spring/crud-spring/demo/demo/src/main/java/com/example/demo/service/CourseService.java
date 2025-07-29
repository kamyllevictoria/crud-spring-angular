package com.example.demo.service;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.CoursePageDTO;
import com.example.demo.dto.mapper.CourseMapper;
import com.example.demo.enums.Status;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
@Validated
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public CoursePageDTO list(int page,  @Positive @Max(100) int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<Course> pageResult = courseRepository.findAll(PageRequest.of(page, size));
        List<CourseDTO> courses = pageResult.get().map(courseMapper:: toDTO).collect(Collectors.toList());
        return new CoursePageDTO(courses, pageResult.getTotalElements(), pageResult.getTotalPages()); //retornamos um objeto que e uma pagina de curso
    }

    public CourseDTO findById(@NotNull @Positive Long id){
        return courseRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course){
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid @NotNull CourseDTO courseDTO){
        return courseRepository.findById(id)
                .map(recordFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertCategoryValue(courseDTO.category()));
                    recordFound.getLessons().clear();
                    course.getLessons().forEach(recordFound.getLessons()::add);
                   return courseMapper.toDTO(courseRepository.save(recordFound));
                }).orElseThrow(()-> new RecordNotFoundException(id));
    }

    public void delete (@PathVariable @NotNull @Positive Long id){
        courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
