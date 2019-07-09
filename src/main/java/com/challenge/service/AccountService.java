package com.challenge.service;

import com.challenge.model.Account;
import com.challenge.rest.Response;
import lombok.NonNull;

import java.math.BigDecimal;

public interface AccountService {

    Response<Account> createAccount(@NonNull Long customerId, @NonNull BigDecimal availableBalance, @NonNull String currency);

    Response<Account> queryAccount(@NonNull String accountId);

    Response<Account> queryAccountBalance(@NonNull String accountId);

    Response<Account> updateAccount(@NonNull String accountId, @NonNull Long customerId, @NonNull BigDecimal availableBalance, @NonNull String currency);
}
