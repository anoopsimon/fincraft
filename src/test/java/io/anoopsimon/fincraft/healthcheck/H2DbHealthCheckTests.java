package io.anoopsimon.fincraft.healthcheck;

import io.anoopsimon.fincraft.util.BaseTest;
import org.junit.jupiter.api.Test;

public class H2DbHealthCheckTests extends BaseTest {


    @Test
    void testH2ConsoleAccess() {
        authorizedRequest()
                .when()
                .get("/h2-console")
                .then()
                .statusCode(200);
    }
}
