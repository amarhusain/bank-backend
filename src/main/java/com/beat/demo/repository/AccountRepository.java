package com.beat.demo.repository;

import com.beat.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // Find account by account number
    Optional<Account> findByAccountNumber(String accountNumber);
}
