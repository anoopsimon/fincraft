package io.anoopsimon.fincraft.unit;


import io.anoopsimon.fincraft.controller.AuthController;
import io.anoopsimon.fincraft.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(AuthController.class)

@AutoConfigureMockMvc(addFilters = false) // <--- disable Spring Security filters
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testGenerateToken_Success() throws Exception {
        // Arrange
        String expectedToken = "test-token-value";
        when(jwtUtil.generateToken(Mockito.eq("system"), anyString()))
                .thenReturn(expectedToken);

        // Act & Assert
        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"secret\":\"mysecret\", \"scope\":\"test-scope\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken));
    }

    @Test
    void testGenerateToken_InvalidSecret() throws Exception {
        // Arrange - no need to mock jwtUtil here

        // Act & Assert
        mockMvc.perform(post("/auth/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"secret\":\"wrongsecret\", \"scope\":\"test-scope\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error").value("Invalid secret"));
    }
}

