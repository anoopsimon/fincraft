package io.anoopsimon.fincraft.unit;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.anoopsimon.fincraft.controller.CustomerController;
import io.anoopsimon.fincraft.model.Customer;
import io.anoopsimon.fincraft.repository.CustomerRepository;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)  // Disable Spring Security filters
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/customers - creates a new customer and returns 200 OK by default (or 201 if desired)")
    void createCustomer_ReturnsNewCustomer() throws Exception {
        // Given: a request body
        Customer requestCustomer = new Customer();
        requestCustomer.setName("Alice");
        requestCustomer.setEmail("alice@example.com");

        // And a mock response when saving
        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setName("Alice");
        savedCustomer.setEmail("alice@example.com");

        Mockito.when(customerRepository.save(any(Customer.class)))
                .thenReturn(savedCustomer);

        // Convert request to JSON
        String requestJson = objectMapper.writeValueAsString(requestCustomer);

        // When & Then
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                // By default, the controller returns the saved entity. That typically implies 200 OK or 201 Created.
                // If you want 201, you can add ResponseEntity logic to the controller.
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.email").value("alice@example.com"));
    }

    @Test
    @DisplayName("GET /api/customers - returns a list of all customers")
    void getAllCustomers_ReturnsList() throws Exception {
        // Given: Some customers in the repository
        Customer c1 = new Customer();
        c1.setId(10L);
        c1.setName("John");
        c1.setEmail("john@example.com");

        Customer c2 = new Customer();
        c2.setId(11L);
        c2.setName("Jane");
        c2.setEmail("jane@example.com");

        Mockito.when(customerRepository.findAll())
                .thenReturn(Arrays.asList(c1, c2));

        // When & Then
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[1].id").value(11L))
                .andExpect(jsonPath("$[1].name").value("Jane"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"));
    }

    @Test
    @DisplayName("GET /api/customers/{id} - returns the existing customer if found")
    void getCustomerById_ReturnsCustomer_WhenFound() throws Exception {
        // Given: a single existing customer
        Customer existingCustomer = new Customer();
        existingCustomer.setId(99L);
        existingCustomer.setName("Bob");
        existingCustomer.setEmail("bob@example.com");

        Mockito.when(customerRepository.findById(eq(99L)))
                .thenReturn(Optional.of(existingCustomer));

        // When & Then
        mockMvc.perform(get("/api/customers/{id}", 99L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99L))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(jsonPath("$.email").value("bob@example.com"));
    }


}
