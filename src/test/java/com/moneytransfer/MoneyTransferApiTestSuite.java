package com.moneytransfer;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.AppModule;
import com.Startup;
import com.entrypoint.EntrypointType;
import com.google.inject.Guice;

import io.javalin.Javalin;

public class MoneyTransferApiTestSuite {

	@BeforeClass
	public synchronized static void setup() {
		String [] args = {"8000"};
		var injector = Guice.createInjector(new AppModule());
        injector.getInstance(Startup.class).boot(EntrypointType.REST, args);
        System.out.println("SERVER IS STARTED");
	}

	@AfterClass
	public synchronized static void completed() {
		Javalin.create().stop();
	}
}
