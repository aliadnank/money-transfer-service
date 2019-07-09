package com.challenge;


import com.challenge.controller.AccountController;
import com.challenge.controller.TransactionController;

import static spark.Spark.port;


public class Application {

    public static void main(String... args){
        port(8080);
        AccountController.getInstance().init();
        TransactionController.getInstance().init();
    }
}
