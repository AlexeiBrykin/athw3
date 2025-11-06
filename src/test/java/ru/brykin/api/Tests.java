package ru.brykin.api;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.brykin.api.students.payload.entity.StudentDto;
import ru.brykin.api.students.StudentApi;
import ru.brykin.api.students.payload.entity.StudentDtoRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Nested
public class Tests {
    private StudentDto studentDtoA;
    private StudentDto studentDtoB;
    private StudentDtoRequest studentDtoC;
    private StudentDtoRequest studentDtoD;
    private StudentApi studentApi;
    private int IDA = 1;
    private final int IDB = 2;
    private boolean isCreated;
    private final List<Integer> marksStudentA = new ArrayList<>(List.of(4, 5));
    private final List<Integer> marksStudentB = new ArrayList<>(List.of(3));
    private final String nameStudentA = "Mark";
    private final String nameStudentB = "Lex";

    @BeforeAll
    static void setUpBeforeAll() {
        // Устанавливаем парсер по умолчанию для JSON
        RestAssured.defaultParser = Parser.JSON;
    }

    @BeforeEach
    void setUp() {
        isCreated = false;
        studentApi = new StudentApi();
        studentDtoA = new StudentDto(IDA, nameStudentA, marksStudentA);
        studentDtoC = new StudentDtoRequest(nameStudentA,marksStudentA);
        studentDtoD = new StudentDtoRequest(IDA, marksStudentA);

        //studentDtoB = createStudentExample(nameStudentB, marksStudentB);
    }

    @AfterEach
    void cleanUp() {
        if (isCreated) {
            studentApi.deleteStudentById(IDA);
        }
    }


    @Test
    void checkId() { //1. get /student/{ID} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
        studentApi.createStudent(studentDtoA);
        isCreated = true;
        StudentDto retrievedStudent = studentApi.getStudentById(IDA);
        assertEquals(IDA, retrievedStudent.getId());
    }

    @Test
    void checkName() { //1. get /student/{ID} возвращает JSON студента с указанным ID и заполненным именем, если такой есть в базе, код 200.
        studentApi.createStudent(studentDtoA);
        isCreated = true;
        StudentDto retrievedStudent = studentApi.getStudentById(IDA);
        assertEquals(nameStudentA, retrievedStudent.getName());
    }

    @Test
    void getStudent404() { //2. get /student/{ID} возвращает код 404, если студента с данным ID в базе нет.
        assertEquals(404, studentApi.getStudent404(-1));
    }

    @Test
    void checkAddStudentNotExisted() { //3. post /student добавляет студента в базу, если студента с таким ID ранее не было, при этом имя заполнено, код 201.
        assertEquals(201, studentApi.createStudentReturnStatus(studentDtoA)); // студент запостился успешно 201 статус
        StudentDto retrievedStudent = studentApi.getStudentById(IDA); // извлечена запись студента
        assertEquals(studentDtoA.getName(), retrievedStudent.getName()); // имя записанного равно имени извлечённого
        isCreated = true;
    }

    @Test
    void checkUpdateStudent() { //4. post /student обновляет студента в базе, если студент с таким ID ранее был, при этом имя заполнено, код 201.
        studentApi.createStudent(studentDtoA); // студент A запостился
        assertEquals(201, studentApi.createStudentReturnStatus(new StudentDto(IDA, nameStudentB, marksStudentA))); // обновлено имя студента A на имя студента B
        StudentDto retrievedStudent = studentApi.getStudentById(IDA); // извлечена запись студента
        assertEquals(nameStudentB, retrievedStudent.getName()); // проверка обновления
        isCreated = true;
    }

    @Test
    void checkCreateWithoutId() { //5. post /student добавляет студента в базу, если ID null, то возвращается назначенный ID, код 201.
        List<Integer> createResult = studentApi.createStudentReturnId(studentDtoC);
        assertEquals(201, createResult.get(0));
        assertEquals(IDA, createResult.get(1));
        isCreated = true;
    }

    @Test
    void checkEmptyName() { //6. post /student возвращает код 400, если имя не заполнено.
        assertEquals(400,studentApi.createStudentReturnStatus(studentDtoD));
        isCreated = false;
    }

    @Test
    void checkDeleteById() { //7. delete /student/{id} удаляет студента с указанным ID из базы, код 200.
        studentApi.createStudent(studentDtoA);
        isCreated = true;
        studentApi.deleteStudentById(IDA);
        assertEquals(404, studentApi.getStudent404(IDA)); //в базе нет удалённого студента
        isCreated = false;
    }

    @Test
    void checkDeleteById404() {//8. delete /student/{id} возвращает код 404, если студента с таким ID в базе нет.
        assertEquals(404,studentApi.deleteStudentById404(-1));
        isCreated = false;
    }

    @Test
    void checkTopStudentEmpty() { //9. get /topStudent код 200 и пустое тело, если студентов в базе нет.
        List<StudentDto> returned = studentApi.getTopStudent();
        System.out.println(returned);
        assertTrue(returned.isEmpty());
        isCreated = false;
    }

//    @Test
//    void checkTopStudent() {
//        studentApi.createStudent(studentDtoA);
//        isCreated = true;
//        List<StudentDto> returned = studentApi.getTopStudent();
//        //assertEquals(studentDtoA, returned.get(0));
//    }
}