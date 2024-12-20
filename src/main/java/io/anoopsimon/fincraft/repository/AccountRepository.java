package io.anoopsimon.fincraft.repository;

import io.anoopsimon.fincraft.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Custom query method to find accounts by customerId
    List<Account> findByCustomerId(Long customerId);
}
