package io.anoopsimon.fincraft.controller;

import io.anoopsimon.fincraft.model.CreditCard;
import io.anoopsimon.fincraft.repository.CreditCardRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {
    private final CreditCardRepository creditCardRepository;

    public CreditCardController(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    // Fetch all credit cards for a customer
    @GetMapping("/customer/{customerId}")
    public List<CreditCard> getCreditCardsByCustomerId(@PathVariable Long customerId) {
        return creditCardRepository.findByCustomerId(customerId);
    }
}
