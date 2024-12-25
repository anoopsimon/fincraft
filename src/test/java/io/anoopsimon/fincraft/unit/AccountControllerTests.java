package io.anoopsimon.fincraft.unit;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.anoopsimon.fincraft.controller.AccountController;
import io.anoopsimon.fincraft.model.Account;
import io.anoopsimon.fincraft.repository.AccountRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false) // <--- disables Spring Security filters
class AccountControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/accounts should create a new account and return 201 Created")
    void createAccount_ShouldReturnCreatedAccount() throws Exception {
        // Given: An incoming request body
        Account requestBody = new Account();
        requestBody.setCustomerId(123L);
        requestBody.setBalance(500.0);

        // And a mock response from the repository
        Account savedAccount = new Account();
        savedAccount.setId(1L);
        savedAccount.setCustomerId(123L);
        savedAccount.setBalance(500.0);

        // When the repository's save method is called, return our mocked "savedAccount"
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        // When & Then
        mockMvc.perform(
                        post("/api/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerId").value(123L))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    @DisplayName("GET /api/accounts/customer/{customerId} should return list of accounts")
    void getAccountsByCustomerId_ShouldReturnListOfAccounts() throws Exception {
        // Given: Some accounts in the repository
        Account account1 = new Account();
        account1.setId(1L);
        account1.setCustomerId(123L);
        account1.setBalance(500.0);

        Account account2 = new Account();
        account2.setId(2L);
        account2.setCustomerId(123L);
        account2.setBalance(1000.0);

        List<Account> accounts = Arrays.asList(account1, account2);

        // Mock the repository response
        Mockito.when(accountRepository.findByCustomerId(123L)).thenReturn(accounts);

        // When & Then
        mockMvc.perform(
                        get("/api/accounts/customer/123")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].customerId").value(123L))
                .andExpect(jsonPath("$[0].balance").value(500.0))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].customerId").value(123L))
                .andExpect(jsonPath("$[1].balance").value(1000.0));
    }
}
