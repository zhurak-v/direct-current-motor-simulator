package com.university.direct_current_motor.core.entities;

public interface Inductable {

    double ZERO_INDUCTANCE = 0.0d;

    double getInductance();

    default boolean isZeroInductance() {
        return this.getInductance() == ZERO_INDUCTANCE;
    }

}
