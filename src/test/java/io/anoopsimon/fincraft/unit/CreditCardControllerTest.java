package io.anoopsimon.fincraft.unit;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.anoopsimon.fincraft.controller.CreditCardController;
import io.anoopsimon.fincraft.model.CreditCard;
import io.anoopsimon.fincraft.repository.CreditCardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CreditCardController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disable security filters
class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/credit-cards/customer/{customerId} - returns 200 with non-empty list")
    void getCreditCardsByCustomerId_Returns200_WhenListIsNotEmpty() throws Exception {
        // Arrange
        CreditCard card1 = new CreditCard();
        card1.setId(101L);
        card1.setCustomerId(123L);
        card1.setCurrentOutstanding(500.0);

        CreditCard card2 = new CreditCard();
        card2.setId(102L);
        card2.setCustomerId(123L);
        card2.setCurrentOutstanding(1000.0);

        List<CreditCard> cards = Arrays.asList(card1, card2);
        Mockito.when(creditCardRepository.findByCustomerId(123L)).thenReturn(cards);

        // Act & Assert
        mockMvc.perform(get("/api/credit-cards/customer/{customerId}", 123L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(101L))
                .andExpect(jsonPath("$[0].customerId").value(123L))
                .andExpect(jsonPath("$[0].currentOutstanding").value(500.0))
                .andExpect(jsonPath("$[1].id").value(102L))
                .andExpect(jsonPath("$[1].customerId").value(123L))
                .andExpect(jsonPath("$[1].currentOutstanding").value(1000.0));
    }

    @Test
    @DisplayName("GET /api/credit-cards/customer/{customerId} - returns 404 when empty list")
    void getCreditCardsByCustomerId_Returns404_WhenListIsEmpty() throws Exception {
        // Arrange
        Mockito.when(creditCardRepository.findByCustomerId(999L)).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/credit-cards/customer/{customerId}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/credit-cards - creates a new credit card, returns 201")
    void createCreditCard_Returns201() throws Exception {
        // Arrange
        CreditCard requestCard = new CreditCard();
        requestCard.setCustomerId(123L);
        requestCard.setCurrentOutstanding(null);

        CreditCard savedCard = new CreditCard();
        savedCard.setId(201L);
        savedCard.setCustomerId(123L);
        savedCard.setCurrentOutstanding(0.0); // defaulted in the controller

        Mockito.when(creditCardRepository.save(any(CreditCard.class))).thenReturn(savedCard);

        // Convert requestCard to JSON
        String requestJson = objectMapper.writeValueAsString(requestCard);

        // Act & Assert
        mockMvc.perform(post("/api/credit-cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(201L))
                .andExpect(jsonPath("$.customerId").value(123L))
                .andExpect(jsonPath("$.currentOutstanding").value(0.0));
    }

    @Test
    @DisplayName("GET /api/credit-cards/{id} - returns 200 when found")
    void getCreditCardById_Returns200_WhenFound() throws Exception {
        // Arrange
        CreditCard existingCard = new CreditCard();
        existingCard.setId(300L);
        existingCard.setCustomerId(888L);
        existingCard.setCurrentOutstanding(450.0);

        Mockito.when(creditCardRepository.findById(eq(300L))).thenReturn(Optional.of(existingCard));

        // Act & Assert
        mockMvc.perform(get("/api/credit-cards/{id}", 300L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(300L))
                .andExpect(jsonPath("$.customerId").value(888L))
                .andExpect(jsonPath("$.currentOutstanding").value(450.0));
    }

    @Test
    @DisplayName("GET /api/credit-cards/{id} - returns 404 when not found")
    void getCreditCardById_Returns404_WhenNotFound() throws Exception {
        // Arrange
        Mockito.when(creditCardRepository.findById(eq(999L))).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/credit-cards/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
