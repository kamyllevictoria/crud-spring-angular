package com.example.demo.controller;

import java.util.List;

import com.example.demo.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;

@Validated
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
   }

    @GetMapping
    public @ResponseBody List<Course> list() {
        return courseService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById (@PathVariable @NotNull @Positive  Long id){
        ResponseEntity<Object> ResponseEntity = null;
        return courseService.findById(id)
                .map(recordFound -> org.springframework.http.ResponseEntity.ok().body(recordFound))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course){
        return courseService.create(course);
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid Course course){
        return courseService.update(id, course)
                .map(recordFound -> {
                    return  ResponseEntity.ok().body(recordFound);
                })
                .orElse(ResponseEntity.notFound().build()).getBody();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete (@PathVariable @NotNull @Positive Long id){
        if(courseService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}


