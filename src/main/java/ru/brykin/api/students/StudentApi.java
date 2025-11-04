package ru.brykin.api.students;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.brykin.api._base._BaseApi;
import ru.brykin.api.students.payload.entity.StudentDto;
import ru.brykin.api.students.payload.entity.StudentDtoNoId;
import ru.brykin.env.Env;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Integer> createStudentReturnId(final StudentDtoNoId student) {
        log.info("Запрошено создание студента с возвратом Id с именем - {}", student.getName());

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS)
                .body(student)
                .post();

        response.then().statusCode(201);

        // Получаем строку из тела ответа
        String responseBody = response.getBody().asString();
        log.info("Тело ответа: {}", responseBody);

        // Проверяем, что тело ответа не пустое
        if (responseBody == null || responseBody.trim().isEmpty()) {
            throw new RuntimeException("Тело ответа пустое");
        }

        // Преобразуем строку в целое число
        int studentId;
        try {
            studentId = Integer.parseInt(responseBody);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Неверный формат ID в ответе: " + responseBody, e);
        }

        log.info("id созданного студента = {}", studentId);
        return new ArrayList<>(List.of(response.getStatusCode(), studentId));
    }

    public void deleteStudentById(final int id) {
        log.info("Удалить студента по id : {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .delete();

        response.then().statusCode(200);
        log.info("Студент успешно удален");
    }
}