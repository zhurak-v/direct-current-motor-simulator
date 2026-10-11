package com.university.direct_current_motor.adapters.desktop.common.components;

import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.values.Resolution;
import javafx.scene.layout.VBox;

import java.util.Objects;

public abstract class Screen extends Component {

    private VBox container;

    protected Screen() {
        super();
        super.getNode().getChildren().add(this.container);
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        this.container = new VBox();
        this.applyStyles();
    }

    private void applyStyles() {
        final InjectionService injectionService = InjectionService.getInjectionService();
        final Resolution resolution = injectionService.getBean(Resolution.class);
        final int minWidth = resolution.getWidth();
        final int minHeight = resolution.getHeight();
        this.container.setStyle("""
            -fx-background-color: #ffffff;
        """);
        this.container.setMinWidth(minWidth);
        this.container.setMinHeight(minHeight);
    }

    @Override
    public void addChildren(final Component... components) {
        if (Objects.nonNull(components)) {
            for (Component component : components) {
                this.container.getChildren().add(component.getNode());
            }
        }
    }

    @Override
    public final void removeChildren(final Component... components) {
        if (Objects.nonNull(components)) {
            for (Component component : components) {
                this.container.getChildren().remove(component.getNode());
            }
        }
    }

    @Override
    public final void addClassName(final String className) {
        this.container.getStyleClass().add(className);
    }

    @Override
    public final void addInlineStyles(final String styles) {
        final String current = this.container.getStyle();
        this.container.setStyle(Objects.isNull(current)
                ? styles
                : current + styles
        );
    }

}
