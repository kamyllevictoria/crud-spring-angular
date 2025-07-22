package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record CourseDTO(
        @JsonProperty("_id")Long id,
        @NotBlank @NotNull @Size(min = 5, max = 100)String name,
        @NotNull @Pattern(regexp = "Back-end|Front-end|Data")
        String category, @Pattern(regexp = "Active|Inactive")
        String status,
        @NotNull @NotEmpty @Valid List<LessonDTO> lessons){

}
