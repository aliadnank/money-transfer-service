package com.challenge.dao;

import com.challenge.config.DependencyConfig;
import com.challenge.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionDaoTest {

    TransactionDao transactionDao;

    @Before
    public void setUp() throws Exception {
        transactionDao = DependencyConfig.getInstance().getTransactionDao();
    }

    @Test
    public void insert() throws Exception {
        Transaction transaction = Transaction.of(Transaction.Status.PROCESSING, Transaction.Type.WITHDRAWL,
                new BigDecimal(1000),100L, UUID.randomUUID(),"test");
        transactionDao.insert(transaction.getId(),transaction);
        Assert.assertTrue(transactionDao.find(transaction.getId()).isPresent());
    }

    @Test
    public void find() throws Exception {
        Transaction transaction = Transaction.of(Transaction.Status.PROCESSING, Transaction.Type.DEPOSIT,
                new BigDecimal(1000),100L, UUID.randomUUID(),"test");
        transactionDao.insert(transaction.getId(),transaction);
        Assert.assertTrue(transactionDao.find(transaction.getId()).isPresent());
    }

}