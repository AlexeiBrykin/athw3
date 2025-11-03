package ru.brykin.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.brykin.api.students.payload.entity.StudentDto;
import ru.brykin.api.students.StudentApi;

import java.util.ArrayList;
import java.util.List;

@Nested
public class Tests {
    private StudentDto studentDto;
    private StudentApi studentApi;
    int a = 1;


    @BeforeEach
    void setUp() {
        List<Integer> marks = new ArrayList<>();
        marks.add(4);
        marks.add(5);
        studentApi = new StudentApi();
        studentDto = new StudentDto()
                .setId(a)
                .setName("Vasya")
                .setMarks(marks);
        System.out.println("created");
    }

   @AfterEach
   void cleanUp() {
       studentApi.deleteStudentById(a);
       System.out.println("Deleted");
   }

    @Test
    void create(){
       studentApi.createStudent(studentDto);
    }

    @Test
    void getChek() {
        studentApi.createStudent(studentDto);
        System.out.println(studentApi.getStudentById(a));
    }
}
