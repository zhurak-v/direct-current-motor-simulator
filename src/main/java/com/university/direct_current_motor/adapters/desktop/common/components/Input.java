package com.university.direct_current_motor.adapters.desktop.common.components;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.Objects;
import java.util.function.BiConsumer;

public class Input extends Component {

    private TextField textField;

    public Input() {
        super();
        this.textField.setFocusTraversable(false);
        this.getNode().getChildren().add(this.textField);
    }

    protected void beforeInit() {
        this.textField = new TextField();
    }

    @Override
    public void addInlineStyles(final String styles) {
        final String current = this.textField.getStyle();

        this.textField.setStyle(Objects.isNull(current)
                ? styles
                : current + styles);
    }

    @Override
    public void removeInlineStyles(final String styles) {
        final String current = this.textField.getStyle();

        if (Objects.isNull(current) || current.isEmpty()) {
            return;
        }

        this.textField.setStyle(current.replace(styles, ""));
    }

    @Override
    public void onClick(
            final BiConsumer<? super MouseEvent, ? super Component> biConsumer
    ) {
        this.textField.setOnMouseClicked(event -> {
            if (Objects.nonNull(biConsumer)) {
                biConsumer.accept(event, this);
            }
        });
    }

    @Override
    public void onHover(
            final BiConsumer<? super MouseEvent, ? super Component> onEntered,
            final BiConsumer<? super MouseEvent, ? super Component> onExited
    ) {
        this.textField.setOnMouseEntered(event -> {
            if (Objects.nonNull(onEntered)) {
                onEntered.accept(event, this);
            }
        });

        this.textField.setOnMouseExited(event -> {
            if (Objects.nonNull(onExited)) {
                onExited.accept(event, this);
            }
        });
    }

    public String getText() {
        return this.textField.getText();
    }

    public void setText(final String text) {
        Objects.requireNonNull(text);
        this.textField.setText(text);
    }

    public void onTarget(final BiConsumer<String, ? super Input> biConsumer) {
        this.textField.textProperty().addListener((observable, previous, current) -> {
            if (Objects.nonNull(biConsumer)) {
                biConsumer.accept(current, this);
            }
        });
    }

}
