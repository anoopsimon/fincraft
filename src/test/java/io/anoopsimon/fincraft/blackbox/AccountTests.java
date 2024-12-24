package io.anoopsimon.fincraft.blackbox;

import io.anoopsimon.fincraft.util.BaseTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class AccountTests extends BaseTest
{
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


}
