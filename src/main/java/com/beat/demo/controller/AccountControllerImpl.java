package com.beat.demo.controller;

import com.beat.demo.payload.response.ApiResponse;
import com.beat.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/accounts")
public class AccountControllerImpl implements AccountController{

    private final AccountService accountService;

    @Autowired public AccountControllerImpl(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createAccount(@RequestParam String accountNumber, @RequestParam String email) {
        ApiResponse<Void> response = accountService.createAccount(accountNumber, email);
        return ResponseEntity.ok(response);
    }

    // Endpoint to credit an amount to the account
    @PostMapping("/{accountNumber}/credit")
    public ResponseEntity<ApiResponse<String>> creditAmount(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        ApiResponse<String> response =  accountService.creditAmount(accountNumber, amount);
        return ResponseEntity.ok(response);
    }

    // Endpoint to debit an amount from the account
    @PostMapping("/{accountNumber}/debit")
    public ResponseEntity<ApiResponse<String>> debitAmount(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        ApiResponse<String> response =  accountService.debitAmount(accountNumber, amount);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get the account balance
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<ApiResponse<BigDecimal>> getBalance(@PathVariable String accountNumber) {
        ApiResponse<BigDecimal> response =  accountService.getBalance(accountNumber);
        return ResponseEntity.ok(response);
    }

}
