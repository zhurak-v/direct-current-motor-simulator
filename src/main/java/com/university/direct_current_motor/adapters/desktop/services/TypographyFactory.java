package com.university.direct_current_motor.adapters.desktop.services;

import com.university.direct_current_motor.adapters.desktop.common.components.Typography;
import jakarta.validation.constraints.NotNull;

public interface TypographyFactory extends ComponentFactory<Typography> {

    @NotNull
    Typography createPrimaryTypography(@NotNull final String text);

}
