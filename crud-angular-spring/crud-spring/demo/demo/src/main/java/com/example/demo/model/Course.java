package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.Length;
import org.hibernate.annotations.SQLDelete;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Data
@Entity
@SQLDelete(sql = "UPDATE course SET status = 'Inactive' WHERE id = ?" ) //sql que desejamos que o hibernate execute toda vez que o metodo delete for chamado
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
    @Size(max = 10)
    @Pattern(regexp = "Back-end|Front-end|Data")
    @Column(length = 10, nullable = false)
    private String category;

    @NotNull
    @Size(max = 10)
    @Pattern(regexp = "Inactive|Active")
    @Column(length = 10, nullable = false)
    private String status = "Active";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
