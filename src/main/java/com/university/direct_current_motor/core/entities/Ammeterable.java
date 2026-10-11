package com.university.direct_current_motor.core.entities;

public interface Ammeterable {

    double ZERO_CURRENT = 0.0d;

    double getCurrent();

    default boolean isZeroCurrent() {
        return this.getCurrent() == ZERO_CURRENT;
    }

}
