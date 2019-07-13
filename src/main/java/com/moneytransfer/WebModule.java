package com.moneytransfer;

import com.entrypoint.AppEntrypoint;
import com.entrypoint.EntrypointType;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;

public class WebModule extends AbstractModule {

    private Javalin app;

    private WebModule(Javalin app) {
        this.app = app;
    }

    @NotNull
    public static WebModule create() {
        return new WebModule(Javalin.create());
    }

    @Override
    protected void configure() {
        bind(Javalin.class).toInstance(app);
        MapBinder.newMapBinder(binder(), EntrypointType.class, AppEntrypoint.class)
                .addBinding(EntrypointType.REST)
                .to(WebEntrypoint.class);
    }
}
