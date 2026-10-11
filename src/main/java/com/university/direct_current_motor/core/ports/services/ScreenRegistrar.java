package com.university.direct_current_motor.core.ports.services;

import com.university.direct_current_motor.infrastructure.services.ScreenRegistrarImpl;

public interface ScreenRegistrar {

    static void register() {
        ScreenRegistrarImpl.register();
    }

}
