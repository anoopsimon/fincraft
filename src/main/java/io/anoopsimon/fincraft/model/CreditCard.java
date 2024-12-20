package io.anoopsimon.fincraft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;
    private Double creditLimit;
    private Double currentOutstanding;

    // Default constructor (required by JPA)
    public CreditCard() {
        this.currentOutstanding = 0.0; // Default to 0.0
    }

    // Constructor with all fields
    public CreditCard(Long customerId, Double creditLimit) {
        this.customerId = customerId;
        this.creditLimit = creditLimit;
        this.currentOutstanding = 0.0;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getCurrentOutstanding() {
        return currentOutstanding;
    }

    public void setCurrentOutstanding(Double currentOutstanding) {
        this.currentOutstanding = currentOutstanding;
    }
}
