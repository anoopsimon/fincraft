package io.anoopsimon.fincraft.controller;

import io.anoopsimon.fincraft.model.Account;
import io.anoopsimon.fincraft.repository.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create a new account
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        // Use the initial balance provided in the request
        account.setBalance(account.getBalance() != null ? account.getBalance() : 0.0);

        // Save the account to the database
        Account savedAccount = accountRepository.save(account);

        // Return the saved account
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    // Fetch all accounts for a customer
    @GetMapping("/customer/{customerId}")
    public List<Account> getAccountsByCustomerId(@PathVariable Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
