package com.university.direct_current_motor.adapters.desktop.common.components;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.Objects;
import java.util.function.BiConsumer;

public abstract class Component {

    private final StackPane root;

    protected Component() {
        this.root = new StackPane();
        this.beforeInit();
    }

    protected void beforeInit() {}

    public final Pane getNode() {
        return this.root;
    }

    public void addChildren(final Component... components) {
        if (Objects.nonNull(components)) {
            for (final Component component : components) {
                this.root.getChildren().add(component.root);
            }
        }
    }

    public void removeChildren(final Component... components) {
        if (Objects.isNull(components)) {
            return;
        }

        for (final Component component : components) {
            this.root.getChildren().remove(component.root);
        }
    }

    public void addClassName(final String className) {
        this.root.getStyleClass().add(className);
    }

    public void removeClassName(final String className) {
        this.root.getStyleClass().remove(className);
    }

    public void addInlineStyles(final String styles) {
        final String current = this.root.getStyle();

        this.root.setStyle(Objects.isNull(current)
            ? styles
            : current + styles);
    }

    public void removeInlineStyles(final String styles) {
        final String current = this.root.getStyle();

        if (Objects.isNull(current) || current.isEmpty()) {
            return;
        }

        this.root.setStyle(current.replace(styles, ""));
    }

    public void onClick(
            final BiConsumer<? super MouseEvent, ? super Component> biConsumer
    ) {
        this.root.setOnMouseClicked(event -> {
            if (Objects.nonNull(biConsumer)) {
                biConsumer.accept(event, this);
            }
        });
    }

    public void onHover(
            final BiConsumer<? super MouseEvent, ? super Component> onEntered,
            final BiConsumer<? super MouseEvent, ? super Component> onExited
    ) {
        this.root.setOnMouseEntered(event -> {
            if (Objects.nonNull(onEntered)) {
                onEntered.accept(event, this);
            }
        });

        this.root.setOnMouseExited(event -> {
            if (Objects.nonNull(onExited)) {
                onExited.accept(event, this);
            }
        });
    }
}