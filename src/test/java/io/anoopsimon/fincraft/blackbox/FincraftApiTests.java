package io.anoopsimon.fincraft.blackbox;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class FincraftApiTests {

    private static String authToken;  // Store JWT here

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

        // 1) Obtain token from /auth/token and store it in authToken
        authToken = given()
                .contentType("application/json")
                .body("{\"secret\": \"mysecret\", \"scope\": \"read\"}")
                .when()
                .post("/auth/token")
                .then()
                .statusCode(200)
                .extract()
                .path("token");  // Adjust if your response field differs
    }

    // ----------------------------------------------------------------
    // Helper method to add Authorization header to each request
    // ----------------------------------------------------------------
    private static io.restassured.specification.RequestSpecification authorizedRequest() {
        return given()
                .header("Authorization", "Bearer " + authToken);
    }

          // ----------------------------------------------------------------
    // Customer Management Tests
    // ----------------------------------------------------------------
    @Test
    void testCreateCustomer() {
        authorizedRequest()
                .contentType("application/json")
                .body("{\"name\": \"Jane Doe\", \"email\": \"jane.doe@example.com\", \"phoneNumber\": \"1234567890\"}")
                .when()
                .post("/api/customers")
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Doe"))
                .body("email", equalTo("jane.doe@example.com"));
    }

    @Test
    void testGetAllCustomers() {
        authorizedRequest()
                .accept("application/json")
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void testGetCustomerById() {
        authorizedRequest()
                .accept("application/json")
                .when()
                .get("/api/customers/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    // ----------------------------------------------------------------
    // Account Management Tests
    // ----------------------------------------------------------------
    @Test
    void testCreateAccount() {
        authorizedRequest()
                .contentType("application/json")
                .body("{\"customerId\": 1, \"accountType\": \"TRANSACTION\", \"balance\": 1000.0}")
                .when()
                .post("/api/accounts")
                .then()
                .statusCode(201)
                .body("accountType", equalTo("TRANSACTION"))
                .body("balance", equalTo(1000.0f));
    }

    @Test
    void testGetAllAccountsForCustomer() {
        authorizedRequest()
                .accept("application/json")
                .when()
                .get("/api/accounts/customer/1")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    // ----------------------------------------------------------------
    // Credit Card Management Tests
    // ----------------------------------------------------------------
    @Test
    void testCreateCreditCard() {
        authorizedRequest()
                .contentType("application/json")
                .body("{\"customerId\": 1, \"creditLimit\": 5000.0}")
                .when()
                .post("/api/credit-cards")
                .then()
                .statusCode(201)
                .body("creditLimit", equalTo(5000.0f))
                .body("currentOutstanding", equalTo(0.0f));
    }

    @Test
    void testGetAllCreditCardsForCustomer() {
        authorizedRequest()
                .accept("application/json")
                .when()
                .get("/api/credit-cards/customer/1")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    // ----------------------------------------------------------------
    // H2 Console Test
    // ----------------------------------------------------------------
    @Test
    void testH2ConsoleAccess() {
        authorizedRequest()
                .when()
                .get("/h2-console")
                .then()
                .statusCode(200);
    }
}
