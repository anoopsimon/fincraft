package io.anoopsimon.fincraft.blackbox;

import io.anoopsimon.fincraft.util.BaseTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class CreditCardTests extends BaseTest
{
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
