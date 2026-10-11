package com.university.direct_current_motor.core.ports.services;

import com.university.direct_current_motor.adapters.desktop.common.components.Screen;
import jakarta.validation.constraints.NotNull;

public interface Router {

    @NotNull
    Screen navigate(@NotNull final String screenName);

    void registerScreen(@NotNull final String screenName,
                        @NotNull final Screen screen);

    void setMainScreen(@NotNull final Screen screen);

}
