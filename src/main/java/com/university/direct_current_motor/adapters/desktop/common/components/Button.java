package com.university.direct_current_motor.adapters.desktop.common.components;

public class Button extends Box {

    public Button() {
        super(Direction.X_DIRECTION);
        this.setButtonAlign();
        this.applyButtonStyles();
    }

    private void applyButtonStyles() {
        this.addInlineStyles("-fx-cursor: hand;");
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        this.getNode().setPickOnBounds(true);
    }

    protected void setButtonAlign() {
        this.setAlign(Align.CENTER,  Align.CENTER);
    }

}
