package io.anoopsimon.fincraft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.anoopsimon.fincraft.model.Account;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Find all accounts by a specific customer ID
    List<Account> findByCustomerId(Long customerId);
}
