package com.challenge.config;

import com.challenge.dao.AccountDao;
import com.challenge.dao.TransactionDao;
import com.challenge.dao.impl.AccountDaoImpl;
import com.challenge.dao.impl.TransactionDaoImpl;
import com.challenge.service.TransactionService;
import com.challenge.service.impl.TransactionServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.challenge.service.AccountService;
import com.challenge.service.impl.AccountServiceImpl;

public class DependencyConfig extends AbstractModule {

    private final Injector injector = Guice.createInjector(this);
    private final static DependencyConfig _INSTANCE = new DependencyConfig();

    private  DependencyConfig(){}

    public static DependencyConfig getInstance(){
        return _INSTANCE;
    }

    @Override
    protected void configure() {
        bind(AccountService.class).to(AccountServiceImpl.class).in(Scopes.SINGLETON);
        bind(TransactionService.class).to(TransactionServiceImpl.class).in(Scopes.SINGLETON);
        bind(AccountDao.class).to(AccountDaoImpl.class).in(Scopes.SINGLETON);
        bind(TransactionDao.class).to(TransactionDaoImpl.class).in(Scopes.SINGLETON);
    }

    public AccountService getAccountService() {
        return injector.getInstance(AccountService.class);
    }

    public TransactionService getTransactionService() {
        return injector.getInstance(TransactionService.class);
    }

    public AccountDao getAccountDao() {
        return injector.getInstance(AccountDao.class);
    }

    public TransactionDao getTransactionDao() {
        return injector.getInstance(TransactionDao.class);
    }


}
