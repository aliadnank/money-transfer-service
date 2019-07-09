package com.challenge.service;

import com.challenge.model.Transaction;
import com.challenge.rest.Response;

import java.math.BigDecimal;

public interface TransactionService {

    Response<Transaction> transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String remarks);

    Response<Transaction> withdraw(String accountNumber, BigDecimal amount, String remarks);

    Response<Transaction> deposit(String accountNumber, BigDecimal amount, String remarks);

    Response<Transaction> queryTransaction(String transactionId);


}
