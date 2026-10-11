package com.university.direct_current_motor.adapters.desktop.services;

import com.university.direct_current_motor.adapters.desktop.common.components.Component;
import jakarta.validation.constraints.NotNull;

public interface ComponentFactory<T extends Component> {

    @NotNull
    T create();

}
