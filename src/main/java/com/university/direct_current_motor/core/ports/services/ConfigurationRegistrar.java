package com.university.direct_current_motor.core.ports.services;

import com.university.direct_current_motor.infrastructure.services.ConfigurationRegistrarImpl;

public interface ConfigurationRegistrar {

    static void register() {
        ConfigurationRegistrarImpl.register();
    }

}
