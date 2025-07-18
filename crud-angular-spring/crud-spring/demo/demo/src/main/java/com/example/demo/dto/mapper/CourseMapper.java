package com.example.demo.dto.mapper;

import com.example.demo.dto.CourseDTO;
import com.example.demo.dto.LessonDTO;
import com.example.demo.enums.Category;
import com.example.demo.enums.Status;
import com.example.demo.model.Course;
import com.example.demo.model.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course){
        if(course == null){
            return null;
        }

        List<LessonDTO> lessons = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                .collect(Collectors.toList());

        return new CourseDTO(
                course.getId(),
                course.getName(),
                course.getCategory().getValue(),
                course.getStatus().getStatus(),
                lessons
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

        List <Lesson> lessons =  courseDTO.lessons().stream().map(lessonDTO ->{
            var lesson = new Lesson();
            lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
            lesson.setCourse(course);
            return lesson;

        }).toList();
        course.setLessons(lessons);


        if (courseDTO.status() != null && !courseDTO.status().trim().isEmpty()) {
            course.setStatus(Status.fromString(courseDTO.status()));
        } else {
            course.setStatus(Status.ACTIVE);
        }
        //criamos cursos com aulas e tambem cosneguimos fazer o update

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