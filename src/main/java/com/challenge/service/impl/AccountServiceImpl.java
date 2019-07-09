package com.challenge.service.impl;

import com.challenge.config.DependencyConfig;
import com.challenge.constants.Constants;
import com.challenge.dao.AccountDao;
import com.challenge.model.Account;
import com.challenge.rest.Response;
import com.challenge.service.AccountService;
import com.google.common.base.Preconditions;
import lombok.NonNull;
import spark.utils.StringUtils;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {

    private final AccountDao accountDao = DependencyConfig.getInstance().getAccountDao();

    @Override
    public Response<Account> createAccount(@NonNull Long customerId, @NonNull BigDecimal availableBalance, @NonNull String currency) {
        try {
            Preconditions.checkArgument(customerId != null, Constants.CUSTOMER_ID_IS_REQUIRED);
            Preconditions.checkArgument(availableBalance != null, Constants.AVAILABLE_BALANCE_IS_REQUIRED);
            Preconditions.checkArgument(currency != null, Constants.CURRENCY_IS_REQUIRED);
            Account account = Account.of(customerId, availableBalance, validateCurrency(currency));
            accountDao.insert(account.getId(),account);
            return new Response<>(Response.Status.SUCCESS, account,Constants.ACCOUNT_CREATED_SUCCESSFULLY);
        } catch (IllegalArgumentException e) {
            return new Response<>(Response.Status.FAILED, null, e.getMessage());
        }
    }

    @Override
    public Response<Account> queryAccount(@NonNull String accountId) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(accountId), Constants.INVALID_ACCOUNT_ID_IS_PROVIDED);
            Optional<Account> account = accountDao.find(UUID.fromString(accountId));
            Preconditions.checkArgument(account.isPresent(), Constants.ACCOUNT_DOES_NOT_EXIST);
            return new Response<>(Response.Status.SUCCESS,account.get(),account.toString());

        } catch (IllegalArgumentException e) {
            return new Response<>(Response.Status.FAILED,null, e.getMessage());
        }
    }

    @Override
    public Response<Account> queryAccountBalance(@NonNull String accountId) {
        try {
            Preconditions.checkArgument(StringUtils.isNotBlank(accountId), Constants.ACCOUNT_NUMBER_IS_NOT_VALID);
            Optional<Account> account = accountDao.find(UUID.fromString(accountId));
            Preconditions.checkArgument(account.isPresent(), Constants.ACCOUNT_DOES_NOT_EXIST);
            return new Response<>(Response.Status.SUCCESS, account.get(),Constants.ACCOUNT_BALANCE_IS +account.get().getBalance());

        } catch (IllegalArgumentException e) {
            return new Response<>(Response.Status.FAILED, null,e.getMessage());
        }
    }

    @Override
    public Response<Account> updateAccount(@NonNull String accountId,@NonNull Long customerId, @NonNull BigDecimal availableBalance, @NonNull String currency) {
        try {
            Preconditions.checkArgument(customerId != null, Constants.CUSTOMER_ID_IS_REQUIRED);
            Preconditions.checkArgument(availableBalance != null, Constants.AVAILABLE_BALANCE_IS_REQUIRED);
            Preconditions.checkArgument(currency != null, Constants.CURRENCY_IS_REQUIRED);
            Optional<Account> accountOptional = accountDao.find(UUID.fromString(accountId));
            Preconditions.checkArgument(accountOptional.isPresent(), Constants.ACCOUNT_DOES_NOT_EXIST);
            Account account = accountOptional.get();
            Preconditions.checkArgument(account.getCustomerId().equals(customerId), Constants.CUSTOMER_ID_DOES_NOT_MATCH);
            account.setBalance(availableBalance);
            account.setCurrency(validateCurrency(currency));
            accountDao.update(account.getId(),account);
            return new Response<>(Response.Status.SUCCESS, account,Constants.ACCOUNT_SUCESSFULLY_UPDATED);

        } catch (IllegalArgumentException e) {
            return new Response<>(Response.Status.FAILED, null, e.getMessage());
        }
    }

    private Account.Currency validateCurrency(String currency) {
        try {
            return Account.Currency.valueOf(currency);
        } catch (IllegalArgumentException e) {
            return Account.Currency.USD;
        }
    }
}
