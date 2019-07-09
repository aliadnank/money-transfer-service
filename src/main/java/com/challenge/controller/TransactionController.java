package com.challenge.controller;

import com.challenge.config.DependencyConfig;
import com.challenge.constants.Constants;
import com.challenge.model.Transaction;
import com.challenge.rest.Response;
import com.challenge.service.TransactionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;

public class TransactionController {

    private static final TransactionController _INSTANCE = new TransactionController();

    private TransactionController() {
        init();
    }

    public static TransactionController getInstance() {
        return _INSTANCE;
    }

    public void init(){
        TransactionService transactionService = DependencyConfig.getInstance().getTransactionService();
        path("/transactions", () -> {
            post("/transfer", (request, response) -> {
                String toAccount = request.queryParams("toAccount");
                String fromAccount = request.queryParams("fromAccount");
                String amount = request.queryParams("amount");
                String error  = null;

                if(!NumberUtils.isCreatable(amount)){
                    error = Constants.INVALID_AMOUNT_WAS_PROVIDED;
                }

                if(!StringUtils.isEmpty(error)){
                    return new Response<String>(Response.Status.FAILED,null,error).toString();
                }

                Response<Transaction> res = transactionService.transferMoney(fromAccount,toAccount,new BigDecimal(amount),"transfer");

                return res;
            });

            post("/withdraw", (request, response) -> {
                String account = request.queryParams("account");
                String amount = request.queryParams("amount");
                String error  = null;

                if(!NumberUtils.isCreatable(amount)){
                    error = Constants.INVALID_AMOUNT_WAS_PROVIDED;
                }

                if(!StringUtils.isEmpty(error)){
                    return new Response<String>(Response.Status.FAILED,null,error).toString();
                }

                Response<Transaction> res = transactionService.withdraw(account,new BigDecimal(amount),"withdraw");

                return res;
            });

            post("/deposit", (request, response) -> {
                String account = request.queryParams("account");
                String amount = request.queryParams("amount");
                String error  = null;

                if(!NumberUtils.isCreatable(amount)){
                    error = Constants.INVALID_AMOUNT_WAS_PROVIDED;
                }

                if(!StringUtils.isEmpty(error)){
                    return new Response<String>(Response.Status.FAILED,null,error).toString();
                }

                Response<Transaction> res = transactionService.deposit(account,new BigDecimal(amount),"deposit");

                return res;
            });

            get("/get", (request, response) -> {
                String transactionId = request.queryParams("transactionId");
                Response<Transaction> res = transactionService.queryTransaction(transactionId);
                return res;
            });


        });

    }
}
