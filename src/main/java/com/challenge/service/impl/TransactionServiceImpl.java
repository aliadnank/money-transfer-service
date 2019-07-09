package com.challenge.service.impl;

import com.challenge.config.DependencyConfig;
import com.challenge.constants.Constants;
import com.challenge.dao.AccountDao;
import com.challenge.dao.TransactionDao;
import com.challenge.model.Account;
import com.challenge.model.Transaction;
import com.challenge.rest.Response;
import com.challenge.service.TransactionService;
import com.google.common.base.Preconditions;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static com.challenge.constants.Constants.FROM_ACCOUNT_WAS_NOT_FOUND;
import static com.challenge.constants.Constants.SUCCESSFULLY_COMPLETED;
import static com.challenge.constants.Constants.TO_ACCCOUNT_WAS_NOT_FOUND;
import static com.challenge.model.Transaction.Status.COMPLETED;
import static com.challenge.model.Transaction.Status.PROCESSING;

public class TransactionServiceImpl implements TransactionService {

    private final AccountDao accountDao = DependencyConfig.getInstance().getAccountDao();
    private final TransactionDao transactionDao = DependencyConfig.getInstance().getTransactionDao();

    @Override
    public Response<Transaction> transferMoney(String fromAccountNumber, String toAccountNumber, BigDecimal amount, String remarks) {

        try {

        Optional<Account> fromAccountOptionals = accountDao.find(UUID.fromString(fromAccountNumber));
        Optional<Account> toAccountOptionals = accountDao.find(UUID.fromString(toAccountNumber));

        Preconditions.checkArgument(fromAccountOptionals.isPresent(),
                FROM_ACCOUNT_WAS_NOT_FOUND);

        Preconditions.checkArgument(toAccountOptionals.isPresent(),
                TO_ACCCOUNT_WAS_NOT_FOUND);

        Preconditions.checkArgument(fromAccountOptionals.get().getCurrency().equals(toAccountOptionals.get().getCurrency()),
                Constants.CURRENCY_DO_NOT_MATCH);

        Preconditions.checkArgument(fromAccountOptionals.get().getBalance().compareTo(amount) > 0,
                Constants.INSUFFICIENT_BALANCE_IN_ACCOUNT);

            /**
             * for this particular function we will have couple of transactions, one transaction of
             * withdrawl for fromAccount and deposit for toAccount
             */

            handleTransaction(fromAccountNumber, amount, remarks, Transaction.Type.WITHDRAWL);
            handleTransaction(toAccountNumber, amount, remarks, Transaction.Type.DEPOSIT);

            return new Response<>(Response.Status.SUCCESS, null, SUCCESSFULLY_COMPLETED);
        } catch (IllegalArgumentException e) {
            final Response<Transaction> response = new Response<>(Response.Status.FAILED, null, e.getMessage());
            return response;
        }
    }

    @Override
    public Response<Transaction> withdraw(String accountNumber, BigDecimal amount, String remarks) {
        try {

            Transaction transaction = handleTransaction(accountNumber, amount, remarks, Transaction.Type.WITHDRAWL);

            return new Response<>(Response.Status.SUCCESS, transaction, SUCCESSFULLY_COMPLETED);
        } catch (IllegalArgumentException e) {
            final Response<Transaction> response = new Response<>(Response.Status.FAILED, null, e.getMessage());
            return response;
        }
    }

    @Override
    public Response<Transaction> deposit(String accountNumber, BigDecimal amount, String remarks) {
        try {

           Transaction transaction =  handleTransaction(accountNumber, amount, remarks, Transaction.Type.DEPOSIT);

            return new Response<>(Response.Status.SUCCESS, transaction, SUCCESSFULLY_COMPLETED);
        } catch (IllegalArgumentException e) {
            final Response<Transaction> response = new Response<>(Response.Status.FAILED, null, e.getMessage());
            return response;
        }
    }

    @Override
    public Response<Transaction> queryTransaction(String transactionId) {
        try {

        Optional<Transaction> transaction = transactionDao.find(UUID.fromString(transactionId));
        Preconditions.checkArgument(transaction.isPresent(), "Transaction was nor found for id "+transactionId);
        return new Response<>(Response.Status.SUCCESS, transaction.get(), SUCCESSFULLY_COMPLETED);

    } catch (IllegalArgumentException e) {
        final Response<Transaction> response = new Response<>(Response.Status.FAILED, null, e.getMessage());
        return response;
    }
    }

    private Transaction handleTransaction(String accountNumber, BigDecimal amount, String remarks, Transaction.Type type)  {
        Optional<Account> accountOptionals = accountDao.find(UUID.fromString(accountNumber));

        Preconditions.checkArgument(accountOptionals.isPresent(), Constants.ACCOUNT_DOES_NOT_EXIST);

        Account account = accountOptionals.get();

        Transaction transaction = Transaction.of(PROCESSING, type, amount, account.getCustomerId(), account.getId()
                , remarks);
        transactionDao.insert(transaction.getId(),transaction);

        switch (type) {
            case WITHDRAWL:
                account.getBalance().subtract(amount);
                break;
            case DEPOSIT:
                account.getBalance().add(amount);
        }

        transaction.setStatus(COMPLETED);
        transactionDao.update(transaction.getId(),transaction);

        return transaction;
    }
}
