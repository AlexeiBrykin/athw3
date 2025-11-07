package ru.brykin.api.students;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.brykin.api._base._BaseApi;
import ru.brykin.api.students.payload.entity.StudentDto;
import ru.brykin.api.students.payload.entity.StudentDtoRequest;
import ru.brykin.env.Env;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class StudentApi extends _BaseApi {
    public StudentApi() {
        super(Env.API.API_CONFIG);
    }

    public StudentDto getStudentById(final int id) {
        log.info("Получить студента по id = {}", id);
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .get();

        response.then().statusCode(200);
        StudentDto student = response.as(StudentDto.class);
        log.info("Полученный студент: {}", student.getId());
        return student;
    }

    public int getStudent404(final int id) {
        log.info("Получить статус 404 студента по id = {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .get();

        int statusCode = response.statusCode();
        log.info("Статус-код: {}", statusCode);
        return statusCode;
    }

    public void createStudent(final StudentDto student) {
        log.info("Создать студента с именем - {}", student.getName());
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS)
                .body(student)
                .post();

        response.then().statusCode(201);
        log.info("Студент успешно создан");
    }
    public void createStudent(final StudentDtoRequest student) {
        log.info("Создать студента с именем - {}", student.getName());
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS)
                .body(student)
                .post();

        response.then().statusCode(201);
        log.info("Студент успешно создан");
    }

    public int createStudentReturnStatus(final StudentDto student) {
        log.info("Запрошено создание студента с возвратом статуса с именем - {}", student.getName());
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS)
                .body(student)
                .post();

        int statusCode = response.statusCode();
        log.info("Статус-код после создания студента: {}", statusCode);
        return statusCode;
    }

    public List<Integer> createStudentReturnId(final StudentDtoRequest student) {
        log.info("Запрошено создание студента с возвратом Id с именем - {}", student.getName());

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS)
                .body(student)
                .post();
        response.then().statusCode(201);
        return new ArrayList<>(List.of(response.getStatusCode(), Integer.parseInt(response.getBody().asString())));
    }

    public void deleteStudentById(final int id) {
        log.info("Удалить студента по id : {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .delete();

        response.then().statusCode(200);
    }

    public int deleteStudentById404(final int id) {
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .delete();

        response.then().statusCode(404);
        return response.statusCode();
    }

    public int createStudentReturnStatus(final StudentDtoRequest student) {
        log.info("Запрошено создание студента с возвратом статуса без именем");
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS)
                .body(student)
                .post();
        return response.statusCode();
    }

    public List<StudentDto> getTopStudent() {
        log.info("Получить лучшего студента");
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_TOP_STUDENT)
                .get();
        response.then().statusCode(200);
        if (response.body().asString().trim().isEmpty()) {
            log.info("Ответ от сервера пустой");
            return Collections.emptyList();}
        return Arrays.asList(response.jsonPath().getObject("", StudentDto[].class));
    }
    public void deleteAllStudents() {
        log.info("Удалить всех студентов");
        for (int i = 0; i < 10; i++) {
            Response response = jsonAutoAuth()
                    .basePath(StudentUrls.API_STUDENTS + "/" + i)
                    .delete();
        }
    }
}
