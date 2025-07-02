package com.example.demo.dto.mapper;

import com.example.demo.dto.CourseDTO;
import com.example.demo.enums.Category;
import com.example.demo.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course){
        if(course == null){
          return null;
        }
        return new CourseDTO(course.getId(), course.getName(), "Front-end");
    }

    public Course toEntity(CourseDTO courseDTO){
        if(courseDTO == null){
            return null;
        }
        Course course = new Course();
        if(courseDTO.id() != null){
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(Category.FRONTEND);
        course.setStatus("Active");
        return course;
    }
}
