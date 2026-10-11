package com.university.direct_current_motor.adapters.desktop.common.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

public class Typography extends Component {

    private static final String DEFAULT_TEXT = "typography";

    private Label label;

    public Typography() {
        this(DEFAULT_TEXT);
    }

    public Typography(final String text) {
        super();
        Objects.requireNonNull(text);
        this.label.setText(text);
        this.label.setWrapText(true);
        this.label.setAlignment(Pos.CENTER);
        this.label.setTextAlignment(TextAlignment.CENTER);
        this.getNode().getChildren().add(this.label);
    }

    @Override
    protected void beforeInit() {
        this.label = new Label();
    }

    @Override
    public final void addInlineStyles(final String styles) {
        final String current = this.label.getStyle();
        this.label.setStyle(Objects.isNull(current)
                ? styles
                : current + styles
        );
    }

    @Override
    public final void removeInlineStyles(final String styles) {
        final String current = this.label.getStyle();

        if (Objects.isNull(current) || current.isEmpty()) {
            return;
        }

        this.label.setStyle(current.replace(styles, ""));
    }

    public final void setText(final String text) {
        this.label.setText(Objects.requireNonNull(text));
    }

    public final String getText() {
        return this.label.getText();
    }

}
