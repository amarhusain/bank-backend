package com.beat.demo.service;

import com.beat.demo.entity.Account;
import com.beat.demo.entity.User;
import com.beat.demo.exception.DuplicateRecordException;
import com.beat.demo.exception.ResourceNotFoundException;
import com.beat.demo.payload.response.ApiResponse;
import com.beat.demo.payload.response.UserResponse;
import com.beat.demo.repository.AccountRepository;
import com.beat.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ApiResponse<Void> createAccount(String accountNumber, String email) {
        // Check if the user exists
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if the account already exists for this account number
        if (accountRepository.findByAccountNumber(accountNumber).isPresent()) {
            throw new DuplicateRecordException("Account with number " + accountNumber + " already exists!");
        }

        // Create a new account
        try{
            Account account = new Account();
            account.setAccountNumber(accountNumber);
            account.setBalance(BigDecimal.ZERO);  // Initial balance is 0
            account.setUser(user);  // Associate the account with the user
            accountRepository.save(account);  // Save the account to the database
            return new ApiResponse<Void>("Account created successful", null);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateRecordException("Account with number " + accountNumber + " already exists!");
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ApiResponse<String> creditAmount(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account with number " + accountNumber + " not found."));

        account.credit(amount);
        accountRepository.save(account);
        return new ApiResponse<String>("Amount credited to ", account.getAccountNumber());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ApiResponse<String> debitAmount(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account with number " + accountNumber + " not found."));

        account.debit(amount);
        accountRepository.save(account);
        return new ApiResponse<String>("Amount debited from ", account.getAccountNumber());

    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ApiResponse<BigDecimal> getBalance(String accountNumber) {

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account with number " + accountNumber + " not found."));

        return new ApiResponse<>("Account balance", account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO);
    }

}
