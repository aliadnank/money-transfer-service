package com.challenge.service;

import com.challenge.config.DependencyConfig;
import com.challenge.model.Account;
import com.challenge.rest.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountServiceTest {

    AccountService accountService;
    Account account;

    @Before
    public void setUp() throws Exception {
        accountService = DependencyConfig.getInstance().getAccountService();
        Response<Account> responseAccCreate = accountService.createAccount(100L, BigDecimal.valueOf(1000),"USD");
        account = responseAccCreate.getModel();
    }

    @Test
    public void createAccount() throws Exception {
        Response<Account> responseAccCreate = accountService.createAccount(100L, BigDecimal.valueOf(1000),"USD");
        Response<Account> responseAccountGet = accountService.queryAccount(responseAccCreate.getModel().getId().toString());
        Assert.assertNotNull(responseAccountGet.getModel());
    }

    @Test
    public void queryAccount() throws Exception {
        Response<Account> responseAccountGet = accountService.queryAccount(account.getId().toString());
        Assert.assertNotNull(responseAccountGet.getModel());
    }

    @Test
    public void queryAccountBalance() throws Exception {
        Response<Account> responseAccountGet = accountService.queryAccount(account.getId().toString());
        Assert.assertEquals(new BigDecimal(1000),responseAccountGet.getModel().getBalance());
    }

    @Test
    public void updateAccount() throws Exception {
        account.setBalance(new BigDecimal(2000));
        accountService.updateAccount(account.getId().toString(), account.getCustomerId(),new BigDecimal(2000),"EUR");
        Response<Account> responseAccountGet = accountService.queryAccount(account.getId().toString());
        Assert.assertEquals(new BigDecimal(2000),responseAccountGet.getModel().getBalance());
    }

}