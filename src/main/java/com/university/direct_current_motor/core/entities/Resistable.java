package com.university.direct_current_motor.core.entities;

public interface Resistable {

    double ZERO_RESISTANCE = 0.0d;
    double MIN_RESISTANCE = 1e-9;
    double MAX_RESISTANCE = 1e12d;
    double GROUND_CONDUCTANCE = 1e-12d;

    double getResistance();

    default boolean isZeroResistance() {
        return this.getResistance() == ZERO_RESISTANCE;
    }

}
