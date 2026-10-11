package com.university.direct_current_motor.application;

public final class Application {

    private Application() {
        throw new UnsupportedOperationException();
    }

    public static void main(final String[] args) {
        final ApplicationRunner applicationRunner = new ApplicationRunner(
                "com.university.direct_current_motor", args
        );
        applicationRunner.run();

    }

}
