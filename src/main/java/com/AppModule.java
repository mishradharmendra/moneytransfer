package com;

import com.google.inject.AbstractModule;
import com.moneytransfer.MoneyTransferModule;

public class AppModule extends AbstractModule {

    protected void configure() {
        bind(Startup.class);
        install(new MoneyTransferModule());
    }
}
