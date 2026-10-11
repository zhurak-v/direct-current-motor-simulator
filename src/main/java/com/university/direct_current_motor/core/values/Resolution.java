package com.university.direct_current_motor.core.values;

public enum Resolution {

    R_2560x1440(2560, 1440),
    R_1920x1080(1920, 1080),
    R_1440x810(1440, 810),
    R_1280x720(1280, 720);

    private final int width;
    private final int height;

    Resolution(final int width, final int height) {
        this.width = width;
        this.height = height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final AspectRatio aspectRatio() {
        return AspectRatio.of(this.width, this.height);
    }

}
