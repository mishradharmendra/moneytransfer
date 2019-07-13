package com.moneytransfer;

import com.google.inject.AbstractModule;
import com.moneytransfer.account.AccountModule;

public class MoneyTransferModule extends AbstractModule {

    protected void configure() {
        install(new AccountModule());
        install(WebModule.create());
    }
}
