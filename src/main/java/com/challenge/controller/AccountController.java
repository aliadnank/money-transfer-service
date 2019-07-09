package com.challenge.controller;

import com.challenge.config.DependencyConfig;
import com.challenge.constants.Constants;
import com.challenge.model.Account;
import com.challenge.rest.Response;
import com.challenge.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

import static spark.Spark.*;

public class AccountController {

    private static final AccountController _INSTANCE = new AccountController();

    private AccountController() {

    }

    public static AccountController getInstance() {
        return _INSTANCE;
    }

    public void init() {
        AccountService accountService = DependencyConfig.getInstance().getAccountService();
        path("/accounts", () -> {
            post("/create", (request, response) -> {
                String error = null;
                String customerId = request.queryParams("customerId");
                String availableBalance = request.queryParams("availableBalance");
                String currency = request.queryParams("currency");

                if(!NumberUtils.isCreatable(customerId)){
                    error = Constants.INVALID_CUSTOMER_ID_WAS_PROVIDED;
                }
                else if(!NumberUtils.isCreatable(availableBalance)){
                    error = Constants.INVALID_AVAILABLE_BALANCE_WAS_PROVIDED;
                }
                else if(StringUtils.isBlank(currency)){
                    error = Constants.CURRENCY_IS_REQUIRED;
                }

                if(!StringUtils.isEmpty(error)){
                    return new Response<String>(Response.Status.FAILED,null,error).toString();
                }

                Response<Account> res = accountService.createAccount(Long.valueOf(customerId),
                        BigDecimal.valueOf(Long.valueOf(availableBalance)), currency);

                return res.toString();
            });


            put("/update", (request, response) -> {
                String error = null;
                String accountId = request.queryParams("accountId");
                String customerId = request.queryParams("customerId");
                String availableBalance = request.queryParams("availableBalance");

                if(!NumberUtils.isCreatable(customerId)){
                    error = Constants.INVALID_CUSTOMER_ID_WAS_PROVIDED;
                }
                else if(StringUtils.isBlank(accountId)){
                    error = Constants.INVALID_ACCOUNT_ID_WAS_PROVIDED;
                }
                else if(!NumberUtils.isCreatable(availableBalance)){
                    error = Constants.INVALID_AVAILABLE_BALANCE_WAS_PROVIDED;
                }

                if(!StringUtils.isEmpty(error)){
                    return new Response<String>(Response.Status.FAILED,null,error).toString();
                }

                String currency = request.queryParams("currency");
                Response<Account> res = accountService.updateAccount(accountId,Long.valueOf(customerId),
                        BigDecimal.valueOf(Long.valueOf(availableBalance)), currency);

                return res.toString();
            });

            get("/get", (request, response) -> {
                String accountId = request.queryParams("accountId");
                Response<Account> res = accountService.queryAccount(accountId);
                return res.toString();
            });
            get("/balance", (request, response) -> {
                String accountId = request.queryParams("accountId");
                Response<Account> res = accountService.queryAccountBalance(accountId);
                return res.toString();
            });
        });

    }


}
