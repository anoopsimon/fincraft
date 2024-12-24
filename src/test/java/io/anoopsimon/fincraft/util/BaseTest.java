package io.anoopsimon.fincraft.util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    private static String authToken;  // Store JWT here

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = ConfigUtil.getConfig("TEST_API_URL", "http://localhost:8080");

        authToken = given()
                .contentType("application/json")
                .body("{\"secret\": \"mysecret\", \"scope\": \"read\"}")
                .when()
                .post("/auth/token")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    // ----------------------------------------------------------------
    // Helper method to add Authorization header to each request
    // ----------------------------------------------------------------
    protected io.restassured.specification.RequestSpecification authorizedRequest() {
        return given()
                .header("Authorization", "Bearer " + authToken);
    }

    protected io.restassured.specification.RequestSpecification simpleRequest() {
        return given()
                .header("Authorization", "Bearer " + authToken);
    }
}
