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
    private StudentDto studentDtoA;
    private StudentDto studentDtoB;
    private StudentApi studentApi;
    private final int IDA = 1;
    private final int IDB = 2;
    private boolean isCreated;
    private final List<Integer> marksStudentA = new ArrayList<>(List.of(4,5));
    private final List<Integer> marksStudentB = new ArrayList<>(List.of(3));
    private final String nameStudentA = "Mark";
    private final String nameStudentB = "Lex";

    @BeforeEach
    void setUp() {
        isCreated = false;
        studentApi = new StudentApi();
        studentDtoA = createStudentExample(IDA, nameStudentA, marksStudentA);
        studentDtoB = createStudentExample(IDB, nameStudentB, marksStudentB);
    }

    private StudentDto createStudentExample(int id, String name, List<Integer> marks) {
        return new StudentDto().setId(id).setName(name).setMarks(marks);
    }

   @AfterEach
   void cleanUp() {
        if (isCreated)
       studentApi.deleteStudentById(IDA);
   }

    @Test
    void checkId() {    //1. get /student/{ID} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
        studentApi.createStudent(studentDtoA);
        isCreated = true;
        StudentDto retrievedStudent = studentApi.getStudentById(IDA);
        assertEquals(studentDtoA.getId(), retrievedStudent.getId());
    }
    @Test
    void checkName() {    //1. get /student/{ID} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
        studentApi.createStudent(studentDtoA);
        isCreated = true;
        StudentDto retrievedStudent = studentApi.getStudentById(IDA);
        assertEquals(studentDtoA.getName(), retrievedStudent.getName());
    }
    @Test
    void getStudent404() { //2. get /student/{ID} возвращает код 404, если студента с данным ID в базе нет.
        assertEquals(404,studentApi.getStudent404(-1));
    }
    @Test
    void checkAddStudentCode() { //3. post /student добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201.
        assertEquals(201,studentApi.createStudentReturnStatus(studentDtoA));
        isCreated = true;
    }
}
