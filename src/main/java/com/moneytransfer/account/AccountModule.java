package com.moneytransfer.account;

import com.entrypoint.Routing;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.moneytransfer.account.repositories.AccountRepositoryModule;
import com.moneytransfer.account.services.AccountServiceModule;

public class AccountModule extends AbstractModule {

    protected void configure() {
        bind(AccountController.class);
        install(new AccountServiceModule());
        install(new AccountRepositoryModule());
        Multibinder.newSetBinder(binder(), Routing.class).addBinding().to(AccountRouting.class);
    }
}
