package com.entrypoint;

import com.google.inject.Inject;
import com.google.inject.Injector;

import java.lang.reflect.ParameterizedType;

public abstract  class Routing<T> {
    @Inject
    private Injector injector;

    private Class<T> controller;

    protected Routing() {
    }

    public abstract void bindRoutes();

    public T getController() {
        return injector.getInstance(getControllerFromGenericType());
    }

    @SuppressWarnings("unchecked")
	private Class<T> getControllerFromGenericType() {
        if (controller == null) {
            controller = (Class<T>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return controller;
    }
}
