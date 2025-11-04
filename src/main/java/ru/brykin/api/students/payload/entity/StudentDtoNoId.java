package ru.brykin.api.students.payload.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
public class StudentDtoNoId {
    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("marks")
    private List<Integer> marks;

    // конструктор без id
    public StudentDtoNoId(String name, List<Integer> marks) {
        this.name = name;
        this.marks = marks;
    }

    // конструктор без параметров
    public StudentDtoNoId() {
    }

    public StudentDtoNoId(int id, List<Integer> marksStudentA) {
        this.id = id;
        this.marks = marksStudentA;
    }
}