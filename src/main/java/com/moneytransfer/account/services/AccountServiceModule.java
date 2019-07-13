package com.moneytransfer.account.services;

import com.google.inject.AbstractModule;

public class AccountServiceModule extends AbstractModule {

    protected void configure() {
        bind(AccountService.class).to(AccountServiveImpl.class);
    }
}
