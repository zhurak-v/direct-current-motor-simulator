package com.university.direct_current_motor.adapters.desktop.common.components;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Objects;

public final class Canvas extends Component {

    private Pane container;

    public Canvas(final double width, final double height) {
        super();
        this.container.setPrefSize(width, height);
        this.container.setMinSize(width, height);
        this.container.setMaxSize(width, height);
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        this.container = new Pane();
        this.getNode().getChildren().add(this.container);
    }

    public void addChildren(
            final double x,
            final double y,
            final Component... components
    ) {
        if (Objects.isNull(components)) {
            return;
        }

        for (final Component component : components) {
            final Node node = component.getNode();

            node.relocate(x, y);

            this.container.getChildren().add(node);
        }
    }

    public void addChildrenRelative(
            final Component relativeTo,
            final double offsetX,
            final double offsetY,
            final Component... components
    ) {
        if (Objects.isNull(relativeTo) || Objects.isNull(components)) {
            return;
        }

        final Node relativeNode = relativeTo.getNode();

        final double x = relativeNode.getLayoutX() + offsetX;
        final double y = relativeNode.getLayoutY() + offsetY;

        for (final Component component : components) {
            final Node node = component.getNode();

            node.relocate(x, y);

            this.container.getChildren().add(node);
        }
    }

    @Override
    public void addChildren(final Component... components) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeChildren(final Component... components) {
        if (Objects.isNull(components)) {
            return;
        }
        for (final Component component : components) {
            this.container.getChildren().remove(component.getNode());
        }
    }

    @Override
    public void addInlineStyles(final String styles) {
        final String current = this.container.getStyle();

        this.container.setStyle(Objects.isNull(current)
                ? styles
                : current + styles);
    }

    public void removeInlineStyles(final String styles) {
        final String current = this.container.getStyle();

        if (Objects.isNull(current) || current.isEmpty()) {
            return;
        }

        this.container.setStyle(current.replace(styles, ""));
    }

}
