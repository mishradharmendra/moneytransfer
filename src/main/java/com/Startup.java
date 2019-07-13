package com;

import com.entrypoint.AppEntrypoint;
import com.entrypoint.EntrypointType;
import com.google.inject.Inject;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Singleton
public class Startup {

    @Inject(optional = true)
    private Map<EntrypointType, AppEntrypoint> entrypoints = Collections.emptyMap();

    public void boot(EntrypointType entrypointType, String[] args) {
        var entryPoint = Optional.ofNullable(entrypoints.get(entrypointType));
        entryPoint.orElseThrow(() -> new RuntimeException("Entrypoint not defined")).boot(args);
    }
}
