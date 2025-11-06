package ru.brykin.api.students.payload.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


import javax.annotation.Nullable;
import java.util.List;

@Getter
@Setter
public class StudentDtoRequest {
    @JsonProperty("id")
    @Nullable
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("marks")
    private List<Integer> marks;

    // конструктор без id
    public StudentDtoRequest(String name, List<Integer> marks) {
        this.name = name;
        this.marks = marks;
    }

    // конструктор только имя
    public StudentDtoRequest(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudentDtoRequest(int id, List<Integer> marks) {
        this.id = id;
        this.marks = marks;
    }
}