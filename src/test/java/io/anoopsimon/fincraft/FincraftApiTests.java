package io.anoopsimon.fincraft;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class FincraftApiTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    // User Management Tests
    @Test
    void testGetAllUsers() {
        given()
                .accept("application/json")
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void testGetUserById() {
        given()
                .accept("application/json")
                .when()
                .get("/api/users/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }



    // Customer Management Tests
    @Test
    void testCreateCustomer() {
        given()
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
        given()
                .accept("application/json")
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void testGetCustomerById() {
        given()
                .accept("application/json")
                .when()
                .get("/api/customers/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    // Account Management Tests
    @Test
    void testCreateAccount() {
        given()
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
        given()
                .accept("application/json")
                .when()
                .get("/api/accounts/customer/1")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }



    // Credit Card Management Tests
    @Test
    void testCreateCreditCard() {
        given()
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
        given()
                .accept("application/json")
                .when()
                .get("/api/credit-cards/customer/1")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    // H2 Console Test
    @Test
    void testH2ConsoleAccess() {
        given()
                .when()
                .get("/h2-console")
                .then()
                .statusCode(200);
    }
}