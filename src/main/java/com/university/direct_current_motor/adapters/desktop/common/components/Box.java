package com.university.direct_current_motor.adapters.desktop.common.components;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Box extends Component {

    public enum Align {
        START,
        CENTER,
        END,
        SPACE_BETWEEN,
        SPACE_AROUND
    }

    public static final class Direction {

        private enum Axis {
            X,
            Y,
        }

        public static final Direction X_DIRECTION;
        public static final Direction Y_DIRECTION;

        static {
            X_DIRECTION = new Direction(Axis.X);
            Y_DIRECTION = new Direction(Axis.Y);
        }

        private final Axis axis;

        private Direction(final Axis axis) {
            this.axis = Objects.requireNonNull(axis);
        }

        public boolean isHorizontal() {
            return axis == Axis.X;
        }

        public boolean isVertical() {
            return axis == Axis.Y;
        }
    }

    private Direction direction;
    private Pane container;
    private Align mainAlign;
    private Align crossAlign;

    private final List<Component> originalComponents;

    public Box() {
        this(Direction.X_DIRECTION);
    }

    public Box(final Direction direction) {
        this.direction = Objects.requireNonNull(direction);
        this.mainAlign = Align.START;
        this.crossAlign = Align.START;
        this.originalComponents = new ArrayList<>();
        this.updateContainer();
    }

    public final Direction getDirection() {
        return this.direction;
    }

    public final void setDirection(final Direction direction) {
        this.direction = direction;
        this.updateContainer();
        this.refreshLayout();
    }

    public final void setAlign(final Align mainAlign, final Align crossAlign) {
        this.mainAlign = Objects.requireNonNull(mainAlign);
        this.crossAlign = Objects.requireNonNull(crossAlign);
        this.refreshLayout();
    }

    public final void bindWidthPercent(final Component parent, final double percent) {
        this.getNode().prefWidthProperty().bind(
                Bindings.createDoubleBinding(
                        () -> parent.getNode().getLayoutBounds().getWidth() * (percent / 100d),
                        parent.getNode().layoutBoundsProperty()
                )
        );
    }

    public final void bindWidthPercent(
            final Component parent,
            final double percent,
            final double minusPx
    ) {
        this.getNode().prefWidthProperty().bind(
                Bindings.createDoubleBinding(
                        () -> parent.getNode().getLayoutBounds().getWidth()
                                * (percent / 100d)
                                - minusPx,
                        parent.getNode().layoutBoundsProperty()
                )
        );
    }

    public final void bindHeightPercent(final Component parent, final double percent) {
        this.getNode().prefHeightProperty().bind(
                Bindings.createDoubleBinding(
                        () -> parent.getNode().getLayoutBounds().getHeight() * (percent / 100d),
                        parent.getNode().layoutBoundsProperty()
                )
        );
    }

    public final void bindHeightPercent(
            final Component parent,
            final double percent,
            final double minusPx
    ) {
        this.getNode().prefHeightProperty().bind(
                Bindings.createDoubleBinding(
                        () -> parent.getNode().getLayoutBounds().getHeight()
                                * (percent / 100d)
                                - minusPx,
                        parent.getNode().layoutBoundsProperty()
                )
        );
    }

    private void updateContainer() {
        this.getNode().getChildren().clear();

        this.container = this.direction.isHorizontal()
                ? new HBox()
                : new VBox();

        HBox.setHgrow(this.container, Priority.ALWAYS);
        VBox.setVgrow(this.container, Priority.ALWAYS);

        this.getNode().getChildren().add(this.container);
    }

    @Override
    public final void addChildren(final Component... components) {
        if (Objects.isNull(components)) {
            return;
        }

        this.originalComponents.addAll(List.of(components));
        this.refreshLayout();
    }

    @Override
    public final void removeChildren(final Component... components) {
        if (Objects.isNull(components)) {
            return;
        }

        this.originalComponents.removeAll(List.of(components));
        this.refreshLayout();
    }

    public final void setMargin(final double top, final double right, final double bottom, final double left) {
        final Insets insets = new Insets(top, right, bottom, left);

        HBox.setMargin(this.getNode(), insets);
        VBox.setMargin(this.getNode(), insets);
        StackPane.setMargin(this.getNode(), insets);
    }

    public final void setMargin(final double topBottom, final double leftRight) {
        this.setMargin(topBottom, leftRight, topBottom, leftRight);
    }

    public final void setTopMargin(final double top) {
        this.setMargin(top, 0.0d, 0.0d, 0.0d);
    }

    public final void setBottomMargin(final double bottom) {
        this.setMargin(0.0d, 0.0d, bottom, 0.0d);
    }

    public final void setLeftMargin(final double left) {
        this.setMargin(0.0d, 0.0d, 0.0d, left);
    }

    public final void setRightMargin(final double right) {
        this.setMargin(0.0d, right, 0.0d, 0.0d);
    }

    public final void setPadding(
            final double top,
            final double right,
            final double bottom,
            final double left
    ) {
        this.container.setPadding(new Insets(top, right, bottom, left));
    }

    public final void setPadding(final double topBottom, final double leftRight) {
        this.setPadding(topBottom, leftRight, topBottom, leftRight);
    }

    public final void setTopPadding(final double top) {
        this.setPadding(top, 0.0d, 0.0d, 0.0d);
    }

    public final void setBottomPadding(final double bottom) {
        this.setPadding(0.0d, 0.0d, bottom, 0.0d);
    }

    public final void setLeftPadding(final double left) {
        this.setPadding(0.0d, 0.0d, 0.0d, left);
    }

    public final void setRightPadding(final double right) {
        this.setPadding(0.0d, right, 0.0d, 0.0d);
    }

    public final void setWidth(final double width) {
        this.getNode().setPrefWidth(width);
        this.getNode().setMinWidth(width);
        this.getNode().setMaxWidth(width);
    }

    public final void setHeight(final double height) {
        this.getNode().setPrefHeight(height);
        this.getNode().setMinHeight(height);
        this.getNode().setMaxHeight(height);
    }

    private void refreshLayout() {
        this.container.getChildren().clear();

        if (this.originalComponents.isEmpty()) {
            return;
        }

        this.applyAlignment();

        for (final Component component : this.originalComponents) {
            final Node node = component.getNode();

            if (node instanceof Region region) {
                if (this.direction.isVertical()) {
                    region.setMaxWidth(Region.USE_PREF_SIZE);
                    VBox.setVgrow(region, Priority.NEVER);
                } else {
                    region.setMaxHeight(Region.USE_PREF_SIZE);
                    HBox.setHgrow(region, Priority.NEVER);
                }
            }
        }

        if (this.mainAlign == Align.SPACE_BETWEEN) {
            this.applySpaceBetween();
        } else if (this.mainAlign == Align.SPACE_AROUND) {
            this.applySpaceAround();
        } else {
            for (final Component component : this.originalComponents) {
                this.container.getChildren().add(component.getNode());
            }
        }
    }

    private void applyAlignment() {
        final Pos alignment = this.calculatePos(this.mainAlign, this.crossAlign);

        if (this.container instanceof HBox hBox) {
            hBox.setAlignment(alignment);
        } else if (this.container instanceof VBox vBox) {
            vBox.setAlignment(alignment);
        }
    }

    private Pos calculatePos(final Align main, final Align cross) {
        if (this.direction.isHorizontal()) {
            return mapToPos(cross, main);
        } else {
            return mapToPos(main, cross);
        }
    }

    private Pos mapToPos(final Align vertical, final Align horizontal) {
        final String v = vertical == Align.CENTER ? "CENTER"
                : (vertical == Align.END ? "BOTTOM" : "TOP");

        final String h = horizontal == Align.CENTER ? "CENTER"
                : (horizontal == Align.END ? "RIGHT" : "LEFT");

        if (v.equals("CENTER") && h.equals("CENTER")) return Pos.CENTER;
        return Pos.valueOf(v + "_" + h);
    }

    private void applySpaceBetween() {
        for (int i = 0; i < this.originalComponents.size(); i++) {
            this.container.getChildren().add(this.originalComponents.get(i).getNode());

            if (i < this.originalComponents.size() - 1) {
                this.container.getChildren().add(createSpacer());
            }
        }
    }

    private void applySpaceAround() {
        this.container.getChildren().add(createSpacer());

        for (final Component component : this.originalComponents) {
            this.container.getChildren().add(component.getNode());
            this.container.getChildren().add(createSpacer());
        }
    }

    private Node createSpacer() {
        final Region spacer = new Region();

        if (this.direction.isHorizontal()) {
            HBox.setHgrow(spacer, Priority.ALWAYS);
        } else {
            VBox.setVgrow(spacer, Priority.ALWAYS);
        }

        return spacer;
    }
}