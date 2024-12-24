package io.anoopsimon.fincraft.blackbox;

import io.anoopsimon.fincraft.util.BaseTest;
import io.anoopsimon.fincraft.util.ConfigUtil;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
class FincraftApiTests extends BaseTest {


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


}
