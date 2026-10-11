package com.university.direct_current_motor.core.ports.services;

import jakarta.validation.constraints.NotNull;

@FunctionalInterface
public interface AnnotationHandler {

    void handle(@NotNull final Class<?> clazz);

}
