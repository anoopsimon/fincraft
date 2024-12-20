package io.anoopsimon.fincraft.controller;

import io.anoopsimon.fincraft.model.Account;
import io.anoopsimon.fincraft.repository.AccountRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Fetch all accounts for a customer
    @GetMapping("/customer/{customerId}")
    public List<Account> getAccountsByCustomerId(@PathVariable Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
