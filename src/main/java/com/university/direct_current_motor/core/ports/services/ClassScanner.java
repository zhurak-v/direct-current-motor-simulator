package com.university.direct_current_motor.core.ports.services;

import com.university.direct_current_motor.infrastructure.services.ClassScannerImpl;
import jakarta.validation.constraints.NotNull;

import java.lang.annotation.Annotation;

public interface ClassScanner {

    @NotNull
    static ClassScanner getClassScanner() {
        return ClassScannerImpl.getClassScanner();
    }

    <A extends Annotation> void registerHandler(
            @NotNull final Class<A> annotationClass,
            @NotNull final AnnotationHandler handler
    );

    void scan(@NotNull final String basePackage);

    void scan();

}
