package com.university.direct_current_motor.core.values;

public final class AspectRatio {

    private final double value;

    private AspectRatio(double value) {
        if (value <= 0.0d || Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("Invalid aspect ratio");
        }
        this.value = value;
    }

    public static AspectRatio of(double width, double height) {
        return new AspectRatio(width / height);
    }

    public static AspectRatio of(double value) {
        return new AspectRatio(value);
    }

    public double getValue() {
        return this.value;
    }

    public double getInverseValue() {
        return 1d / this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final AspectRatio aspectRatio)) {
            return false;
        }

        return this.value == aspectRatio.getValue();
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.value);
    }

    @Override
    public String toString() {
        return String.format("aspect-ratio: %.6f", this.value);
    }

}