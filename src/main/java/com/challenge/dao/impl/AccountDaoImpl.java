package com.challenge.dao.impl;

import com.challenge.dao.AbstractDao;
import com.challenge.dao.AccountDao;
import com.challenge.model.Account;

import java.util.Optional;

public class AccountDaoImpl extends AbstractDao<Account> implements AccountDao {

    @Override
    public Optional<Account> findByCustomerId(Long customerId) {
        return ds.values().stream().filter(a -> a.getCustomerId().equals(customerId)).findFirst();
    }
}
