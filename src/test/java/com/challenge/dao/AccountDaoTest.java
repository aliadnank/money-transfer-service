package com.challenge.dao;

import com.challenge.config.DependencyConfig;
import com.challenge.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

public class AccountDaoTest {

    AccountDao accountDao;

    @Before
    public void setUp() throws Exception {
        accountDao = DependencyConfig.getInstance().getAccountDao();
    }

    @Test
    public void insert() throws Exception {
        Account account = Account.of(100L,new BigDecimal(1000), Account.Currency.EUR);
        accountDao.insert(account.getId(),account);
        Assert.assertTrue(accountDao.find(account.getId()).isPresent());
    }

    @Test
    public void find() throws Exception {
        Account account = Account.of(100L,new BigDecimal(1000), Account.Currency.EUR);
        accountDao.insert(account.getId(),account);
        Assert.assertTrue(accountDao.find(account.getId()).isPresent());
    }

    @Test
    public void update() throws Exception {
        Account account = Account.of(100L,new BigDecimal(1000), Account.Currency.EUR);
        accountDao.insert(account.getId(),account);
        account.setBalance(new BigDecimal(2000));
        accountDao.update(account.getId(),account);
        Optional<Account> accountOptional = accountDao.find(account.getId());
        Assert.assertEquals(accountOptional.get().getBalance(),new BigDecimal(2000));
    }

}