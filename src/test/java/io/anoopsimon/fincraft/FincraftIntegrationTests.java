package io.anoopsimon.fincraft;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FincraftIntegrationTests {

    @LocalServerPort
    private int port;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void testCreateCustomer() {
        given()
                .port(port)
                .contentType("application/json")
                .body("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"phoneNumber\": \"1234567890\"}")
                .when()
                .post("/api/customers")
                .then()
                .statusCode(201)
                .body("name", equalTo("John Doe"))
                .body("email", equalTo("john.doe@example.com"));
    }

    @Test
    void testGetAllCustomers() {
        given()
                .port(port)
                .when()
                .get("/api/customers")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void testCreateAccount() {
        given()
                .port(port)
                .contentType("application/json")
                .body("{\"customerId\": 1, \"accountType\": \"TRANSACTION\", \"initialBalance\": 10014.0}")
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
                .port(port)
                .when()
                .get("/api/accounts/customer/1")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void testCreateCreditCard() {
        given()
                .port(port)
                .contentType("application/json")
                .body("{\"customerId\": 1, \"creditLimit\": 5000.0, \"currentOutstanding\": 0.0}")
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
                .port(port)
                .when()
                .get("/api/credit-cards/customer/1")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    void testDepositFunds() {
        given()
                .port(port)
                .contentType("application/json")
                .body("{\"amount\": 500.0}")
                .when()
                .post("/api/accounts/1/deposit")
                .then()
                .statusCode(200)
                .body("newBalance", greaterThan(500.0f));
    }

    @Test
    void testTransferFunds() {
        given()
                .port(port)
                .contentType("application/json")
                .body("{\"fromAccountId\": 1, \"toAccountId\": 2, \"amount\": 200.0}")
                .when()
                .post("/api/accounts/transfer")
                .then()
                .statusCode(200)
                .body("transferAmount", equalTo(200.0f));
    }
}
