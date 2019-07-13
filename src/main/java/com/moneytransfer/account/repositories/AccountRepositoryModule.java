package com.moneytransfer.account.repositories;

import com.google.inject.AbstractModule;

public class AccountRepositoryModule extends AbstractModule {

    protected void configure() {
        bind(AccountRepositories.class).to(AccountRepositoriesImpl.class);
    }
}
