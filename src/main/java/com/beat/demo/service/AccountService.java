package com.beat.demo.service;

import com.beat.demo.entity.Account;
import com.beat.demo.payload.response.ApiResponse;

import java.math.BigDecimal;

public interface AccountService {

    public ApiResponse<Void> createAccount(String accountNumber, String email);

    public ApiResponse<String> creditAmount(String accountNumber, BigDecimal amount);

    public ApiResponse<String> debitAmount(String accountNumber, BigDecimal amount);

    public ApiResponse<BigDecimal> getBalance(String accountNumber);
}
