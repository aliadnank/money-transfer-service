package com.challenge.service;

import com.challenge.config.DependencyConfig;
import com.challenge.model.Account;
import com.challenge.model.Transaction;
import com.challenge.rest.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.challenge.constants.Constants.SUCCESSFULLY_COMPLETED;
import static org.junit.Assert.*;

public class TransactionServiceTest {

    TransactionService transactionService;
    AccountService accountService;
    Account toAccount, fromAccount;

    @Before
    public void setUp() throws Exception {
        transactionService = DependencyConfig.getInstance().getTransactionService();
        accountService = DependencyConfig.getInstance().getAccountService();
        toAccount = accountService.createAccount(100L,new BigDecimal(1000),"USD").getModel();
        fromAccount = accountService.createAccount(101L,new BigDecimal(2000),"USD").getModel();

    }

    @Test
    public void transferMoney() throws Exception {
        Response<Transaction> response = transactionService.transferMoney(fromAccount.getId().toString(),toAccount.getId().toString(),
                                                                new BigDecimal(500),"test");
        Assert.assertEquals(SUCCESSFULLY_COMPLETED,response.getMessage());
    }


    @Test
    public void withdraw() throws Exception {
        Response<Transaction> response = transactionService.withdraw(fromAccount.getId().toString(),
                new BigDecimal(500),"test");
        Assert.assertNotNull(response.getModel());
    }

    @Test
    public void deposit() throws Exception {
        Response<Transaction> response = transactionService.deposit(toAccount.getId().toString(),
                new BigDecimal(500),"test");
        Assert.assertNotNull(response.getModel());
    }

}