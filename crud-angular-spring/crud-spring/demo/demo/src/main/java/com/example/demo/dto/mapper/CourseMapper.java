package com.example.demo.dto.mapper;

import com.example.demo.dto.CourseDTO;
import com.example.demo.enums.Category;
import com.example.demo.enums.Status; // Importe seu enum Status
import com.example.demo.model.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course){
        if(course == null){
            return null;
        }
        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCategory().getValue(),
                course.getStatus().getStatus()
        );
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
        course.setCategory(convertCategoryValue(courseDTO.category()));

        course.setStatus(Status.fromString(String.valueOf(courseDTO.status())));
        return course;
    }

    public Category convertCategoryValue(String value){
        if(value == null){
            return null;
        }
        Category category = switch (value) {
            case "Back-end" -> Category.BACKEND;
            case "Front-end" -> Category.FRONTEND;
            case "Data" -> Category.DATA;
            default -> throw new IllegalArgumentException("Invalid category: " + value);
        };

        return category;
    }
}