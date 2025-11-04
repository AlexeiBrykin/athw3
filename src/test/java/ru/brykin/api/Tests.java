package ru.brykin.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.brykin.api.students.payload.entity.StudentDto;
import ru.brykin.api.students.StudentApi;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Nested
public class Tests {
    private StudentDto studentDto;
    private StudentApi studentApi;
    private final int ID = 1;
    private boolean isCreated;

    @BeforeEach
    void setUp() {
        isCreated = false;
        List<Integer> marks = new ArrayList<>();
        marks.add(4);
        marks.add(5);
        studentApi = new StudentApi();
        studentDto = new StudentDto()
                .setId(ID)
                .setName("Vasya")
                .setMarks(marks);
    }

   @AfterEach
   void cleanUp() {
        if (isCreated)
       studentApi.deleteStudentById(ID);
   }

    @Test
    void checkId() {    //1. get /student/{id} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
        studentApi.createStudent(studentDto);
        isCreated = true;
        StudentDto retrievedStudent = studentApi.getStudentById(ID);
        assertEquals(studentDto.getId(), retrievedStudent.getId());
    }
    @Test
    void checkName() {    //1. get /student/{id} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
        studentApi.createStudent(studentDto);
        isCreated = true;
        StudentDto retrievedStudent = studentApi.getStudentById(ID);
        assertEquals(studentDto.getName(), retrievedStudent.getName());
    }
    @Test
    void getStudent404() { //2. get /student/{id} возвращает код 404, если студента с данным ID в базе нет.
        studentApi.getStudent404(-1);
        System.out.println("УДАЛИЛСЯ");
    }
}
