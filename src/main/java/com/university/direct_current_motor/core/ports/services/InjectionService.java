package com.university.direct_current_motor.core.ports.services;

import com.university.direct_current_motor.infrastructure.services.InjectionServiceImpl;
import jakarta.validation.constraints.NotNull;

import java.util.function.Supplier;

public interface InjectionService {

    enum Scope {
        PROTOTYPE,
        SINGLETON
    }

    static InjectionService getInjectionService() {
        return InjectionServiceImpl.getInjectionService();
    }

    void registerBean(
            @NotNull final Class<?> clazz,
            @NotNull final String name,
            @NotNull final Supplier<?> factory,
            @NotNull final InjectionService.Scope scope
    );

    @NotNull
    <T> T getBean(@NotNull final Class<?> clazz);

    @NotNull
    <T> T getBean(
            @NotNull final Class<?> clazz,
            @NotNull final String name
    );

}
