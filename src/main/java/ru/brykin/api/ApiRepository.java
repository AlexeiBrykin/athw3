package ru.brykin.api;

import lombok.experimental.UtilityClass;
import ru.brykin.api.students.StudentApi;

@UtilityClass
public class ApiRepository {
    public static final StudentApi studentsApi = new StudentApi();
}
