package com;

import com.entrypoint.EntrypointType;
import com.google.inject.Guice;

/**
 * This is main class which start application also start the embedded server. No need to install separate server to the app.
 */
public class App {
    public static void main(String[] args) {
        var injector = Guice.createInjector(new AppModule());
        injector.getInstance(Startup.class).boot(EntrypointType.REST, args);
    }

}
