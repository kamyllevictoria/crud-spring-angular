package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id){
        ResponseEntity<Object> ResponseEntity = null;
        return courseRepository.findById(id)
                .map(recordFound -> ResponseEntity.ok().body(recordFound))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course){
        return courseRepository.save(course);
        //return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course course){
        //verificar se o registro existe na base de dados
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());

                    Course updated = courseRepository.save(recordFound);
                    return  ResponseEntity.ok().body(updated);
                })
                .orElse(ResponseEntity.notFound().build()).getBody();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        return courseRepository.findById(id)
                .map(recordFound -> {
                    courseRepository.delete(recordFound);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


}


