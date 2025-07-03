package com.example.demo.model;

import com.example.demo.enums.Category;
import com.example.demo.enums.Status;
import com.example.demo.enums.converters.CategoryConverter;
import com.example.demo.enums.converters.StatusConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;


@Entity
@SQLDelete(sql = "UPDATE course SET status = 'Active' WHERE id = ?" )
@Where(clause = "status != 'Inactive'")//sql que desejamos que o hibernate execute toda vez que o metodo delete for chamado
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String name;

    @NotNull
    @Size(min = 5, max = 10)
    @Column(length = 10, nullable = false)
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @NotNull
    @Column(length = 10, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();

    public @NotNull @Size(max = 10) @Pattern(regexp = "Inactive|Active") Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    // Getter personalizado para exibir como "_id"
    @JsonProperty("_id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public @NotNull @Size(max = 10) @Pattern(regexp = "Back-end|Front-end|Data") Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }


    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
        lesson.setCourse(this);
    }

    public void removeLesson(Lesson lesson) {
        this.lessons.remove(lesson);
        lesson.setCourse(null);
    }

}
