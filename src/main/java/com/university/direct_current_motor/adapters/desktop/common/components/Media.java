package com.university.direct_current_motor.adapters.desktop.common.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Objects;

public final class Media extends Box {

    private static final String DEFAULT_IMAGE_PATH = "/icons/image-not-found.png";

    private final ImageView imageView;
    private double ratio = 1;

    public Media() {
        this(DEFAULT_IMAGE_PATH);
    }

    public Media(final String localPath) {
        super(Direction.Y_DIRECTION);

        this.imageView = this.createImageView();
        this.configureContainer();

        this.setImage(localPath);
    }

    public void setImage(final String localPath) {
        Objects.requireNonNull(localPath, "Local path cannot be null");

        try (InputStream inputStream = this.openResource(localPath)) {
            this.setImage(inputStream);
        } catch (Exception exception) {
            throw new RuntimeException(
                    "Failed to load image from path: " + localPath,
                    exception
            );
        }
    }

    public void setImage(final InputStream inputStream) {
        Objects.requireNonNull(inputStream, "Input stream cannot be null");

        final Image image = new Image(inputStream);

        this.imageView.setImage(image);
        this.updateRatio(image);
    }

    public void setHeightByRatio(final double height) {
        final double width = height * this.ratio;
        this.setStaticSize(width, height);
    }

    public void setWidthByRatio(final double width) {
        final double height = width / this.ratio;
        this.setStaticSize(width, height);
    }

    public void setStaticSize(final double width, final double height) {
        this.getNode().setMinSize(width, height);
        this.getNode().setPrefSize(width, height);
        this.getNode().setMaxSize(width, height);
    }

    private InputStream openResource(final String localPath) {
        final InputStream inputStream = Media.class.getResourceAsStream(localPath);

        if (Objects.isNull(inputStream)) {
            throw new IllegalArgumentException(
                    "Resource not found: " + localPath
            );
        }

        return inputStream;
    }

    private ImageView createImageView() {
        final ImageView imageView = new ImageView();

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        imageView.fitWidthProperty().bind(
                this.getNode().prefWidthProperty()
        );

        imageView.fitHeightProperty().bind(
                this.getNode().prefHeightProperty()
        );

        return imageView;
    }

    private void configureContainer() {
        this.getNode().setMinSize(0, 0);
        this.getNode().getChildren().add(this.imageView);
    }

    private void updateRatio(final Image image) {
        final double width = image.getWidth();
        final double height = image.getHeight();

        if (height <= 0) {
            this.ratio = 1;
            return;
        }

        this.ratio = width / height;
    }
}