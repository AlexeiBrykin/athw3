package ru.brykin.api.students;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import ru.brykin.api._base._BaseApi;
import ru.brykin.api.students.payload.entity.StudentDto;
import ru.brykin.env.Env;


@Slf4j
public class StudentApi extends _BaseApi {
    public StudentApi (){
        super(Env.API.API_CONFIG);
    }

    public StudentDto getStudentById(final int id) {
        log.info("Получить студента по id = {}", id);
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .get();

        response.then().statusCode(200);
        return response.as(StudentDto.class);
    }

    public void getStudent404(final int id) {
        log.info("Получить статус 404 студента по id = {}", id);

        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .get();

        response.then().statusCode(404);
    }

    public void createStudent(final StudentDto student) {
        log.info("Создать студент с именем - {}", student.getName());
        Response response = jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS)
                .body(student)
                .post();

        response.then().statusCode(201);
    }

    public void deleteStudentById(final int id) {
        log.info("Удалить студента по id : {}", id);

        jsonAutoAuth()
                .basePath(StudentUrls.API_STUDENTS + "/" + id)
                .delete()
                .then()
                .statusCode(200);
    }
}
