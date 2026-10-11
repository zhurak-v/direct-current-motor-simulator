package com.university.direct_current_motor.adapters.desktop.services;

import com.university.direct_current_motor.adapters.desktop.common.components.Button;
import jakarta.validation.constraints.NotNull;

public interface ButtonFactory extends ComponentFactory<Button> {

    @NotNull
    Button createPrimaryButton();

}
