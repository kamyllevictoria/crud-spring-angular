package com.example.demo.dto;

import com.example.demo.model.Lesson;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public record CourseDTO(
        @JsonProperty("_id")Long id,
        @NotBlank @NotNull @Size(min = 5, max = 100)String name,
        @NotNull @Size(max = 10) @Pattern(regexp = "Back-end|Front-end|Data")
        String category, @Pattern(regexp = "Active|Inactive")
        String status,
        List<LessonDTO> lessons){

}
