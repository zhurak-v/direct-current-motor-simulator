package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.common.components.Input;

public final class PrimaryInput extends Input {

    public PrimaryInput() {
        super();
        this.applyStyles();
    }

    private void applyStyles() {
        this.addInlineStyles("""
            -fx-min-width: 40px;
            -fx-pref-width: 40px;
            -fx-max-width: 40px;
        """);
    }

}
