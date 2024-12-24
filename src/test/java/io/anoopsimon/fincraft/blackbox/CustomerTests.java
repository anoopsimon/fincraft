package io.anoopsimon.fincraft.blackbox;

import io.anoopsimon.fincraft.util.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;

@SpringBootTest
class CustomerTests extends BaseTest {


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



}
