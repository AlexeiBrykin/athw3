package ru.brykin.api._base;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import ru.brykin.env.config.ApiConfig;
import io.restassured.parsing.Parser;

import java.util.List;

@RequiredArgsConstructor
public class _BaseApi {
    protected final ApiConfig CONFIG;
    protected RequestSpecification jsonAutoAuth() {
        return buildRequest();
    }

    protected RequestSpecification buildRequest() {
        return RestAssured.given()
                .config(createConfig())
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .baseUri(CONFIG.url())
                .filters(getFilters());
    }

    private List<Filter> getFilters() {
        return List.of(
                new ResponseLoggingFilter(),
                new ResponseLoggingFilter()
        );
    }

    private RestAssuredConfig createConfig() {
        return RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000));
    }
}
