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
                .map(recordFound -> ResponseEntity.ok().body(recordFound)) //curso encontrado, que e nossa variavel record
                .orElse(ResponseEntity.notFound().build()); //curso nao encontrado, erro 404
    }

    //criar um novo curso
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course){
        return courseRepository.save(course);
        //return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    //tratando o http put
    @PutMapping("/{id}")
    public Course update(@PathVariable Long id, @RequestBody Course course){
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    //atualizar no banco e dados
                    Course updated = courseRepository.save(recordFound);
                    return  ResponseEntity.ok().body(updated);
                }) //curso encontrado, que e nossa variavel record
                .orElse(ResponseEntity.notFound().build()).getBody(); //curso nao encontrado, erro 404
    }

}


