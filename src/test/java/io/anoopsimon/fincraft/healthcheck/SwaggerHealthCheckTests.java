package io.anoopsimon.fincraft.healthcheck;


import io.anoopsimon.fincraft.util.BaseTest;
import org.junit.jupiter.api.Test;

public class SwaggerHealthCheckTests extends BaseTest {

    @Test
    void testSwaggerUiPage() {
        authorizedRequest()
                .when()
                .get("/swagger-ui.html")
                .then()
                .statusCode(200);
    }

    @Test
    void testSwaggerApiDocs() {
        authorizedRequest()
                .when()
                .get("/v3/api-docs")
                .then()
                .statusCode(200);
    }



}

