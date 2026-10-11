package com.university.direct_current_motor.core.entities;

public interface Voltable {

    double ZERO_VOLTAGE = 0.0d;
    double MAX_VOLTAGE = 1e12;

    double getVoltage();

    default boolean isZeroVoltage() {
        return this.getVoltage() == ZERO_VOLTAGE;
    }

}
