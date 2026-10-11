package com.university.direct_current_motor.application;

import com.university.direct_current_motor.core.ports.services.ConfigurationRegistrar;
import com.university.direct_current_motor.core.ports.services.ClassScanner;

public final class ApplicationRunner implements Runnable {

    private final ClassScanner classScanner;
    private final String basePackage;
    private final String[] args;

    public ApplicationRunner(final String basePackage ,final String[] args) {
        this.classScanner = ClassScanner.getClassScanner();
        this.basePackage = basePackage;
        this.args = args;
    }

    @Override
    public void run() {
        this.registerConfiguration();
        this.launchFxApplication();
    }

    private void registerConfiguration() {
        ConfigurationRegistrar.register();
        this.classScanner.scan(this.basePackage);
    }

    private void launchFxApplication() {
        javafx.application.Application.launch(FxApplication.class, this.args);
    }

}
