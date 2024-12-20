package io.anoopsimon.fincraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.anoopsimon.fincraft.model.CreditCard;

import java.util.List;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    // Find all credit cards by a specific customer ID
    List<CreditCard> findByCustomerId(Long customerId);
}
