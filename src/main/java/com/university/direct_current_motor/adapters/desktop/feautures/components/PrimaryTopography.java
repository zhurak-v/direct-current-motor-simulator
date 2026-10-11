package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.common.components.Typography;

import java.util.Objects;

public final class PrimaryTopography extends Typography {

    public PrimaryTopography(final String text) {
        super(text);
        Objects.requireNonNull(text);
        this.applyPrimaryStyles();
    }

    private void applyPrimaryStyles() {
        this.addInlineStyles("""
                -fx-alignment: center;
                -fx-font-size: 16px;
                -fx-font-family: "Roboto";
        """
        );
    }

}
