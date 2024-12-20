package io.anoopsimon.fincraft.controller;

import io.anoopsimon.fincraft.model.CreditCard;
import io.anoopsimon.fincraft.repository.CreditCardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {
    private final CreditCardRepository creditCardRepository;

    public CreditCardController(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    // Fetch all credit cards for a customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CreditCard>> getCreditCardsByCustomerId(@PathVariable Long customerId) {
        List<CreditCard> creditCards = creditCardRepository.findByCustomerId(customerId);
        if (creditCards.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(creditCards);
    }

    // Create a new credit card
    @PostMapping
    public ResponseEntity<CreditCard> createCreditCard(@RequestBody CreditCard creditCard) {
        CreditCard savedCard = creditCardRepository.save(creditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCard);
    }

    // Fetch a specific credit card by ID
    @GetMapping("/{id}")
    public ResponseEntity<CreditCard> getCreditCardById(@PathVariable Long id) {
        Optional<CreditCard> creditCard = creditCardRepository.findById(id);
        if (creditCard.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(creditCard.get());
    }
}
