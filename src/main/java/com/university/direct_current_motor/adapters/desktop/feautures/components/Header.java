package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.common.components.*;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.ports.services.Router;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public final class Header extends Box {

    private static final class Logo extends Box {

        private final Media icon;

        private Logo() {
            super();
            this.icon = this.createIcon();
            this.applyLogoStyles();
            this.render();
        }

        private Media createIcon() {
            return new Media("/icons/university-logo.png");
        }

        private void applyLogoStyles() {
            this.setDirection(Direction.Y_DIRECTION);
            this.setAlign(Align.CENTER,  Align.START);
            this.applyIconStyles();
        }

        private void applyIconStyles() {
            this.icon.setWidthByRatio(45.0d);
        }

        private void render() {
            this.addChildren(this.icon);
        }

    }
    private static final class Navigation extends Box {

        private final List<Button> buttons;

        private Navigation() {
            super();
            this.buttons = this.createNavigationButtons();
            this.applyStyles();
            this.render();
        }

        private Button createNavigationButton(
                final String title,
                final String screenName
        ) {
            Objects.requireNonNull(title);
            Objects.requireNonNull(screenName);

            final InjectionService injectionService = InjectionService.getInjectionService();
            final Router router = injectionService.getBean(Router.class);

            final BiConsumer<? super MouseEvent, ? super Component> onButtonClick
                    = (event, component) -> router.navigate(screenName);

            final Typography typography = new PrimaryTopography(title);
            final Button button = new PrimaryButton();
            button.onClick(onButtonClick);
            button.addChildren(typography);

            return button;
        }

        private List<Button> createNavigationButtons() {
            record ButtonProps(
                    String title, String screenName
            ) {}

            final List<ButtonProps> props = List.of(
                    new ButtonProps("Головна", "home-screen"),
                    new ButtonProps("Як користуватися додатком", "guide-screen"),
                    new ButtonProps("Теоретичні відомості", "theory-screen"),
                    new ButtonProps("Про нас", "about-us-screen")
            );

            return props.stream()
                    .map(p -> this.createNavigationButton(p.title, p.screenName))
                    .toList();
        }

        private void applyStyles() {
            this.setDirection(Direction.X_DIRECTION);
            this.setAlign(Align.SPACE_BETWEEN, Align.CENTER);
            this.applyButtonStyles(this.buttons);
        }

        private void render() {
            this.addChildren(this.buttons.toArray(new Button[0]));
        }

        private void applyButtonStyles(final List<Button> buttons) {
            for (final Button button : buttons) {
                button.setMargin(0.0d, 12.0d);
                button.setPadding(8.0d, 12.0d);
            }
        }

    }

    // TODO auth panel
    private static final class Panel extends Box {

        private Panel() {
            super();
            this.applyStyles();
        }

        private void applyStyles() {
            this.setWidth(45.0d);
        }

    }

    private final Logo logo;
    private final Navigation navigation;
    private final Panel panel;

    public Header() {
        super();
        this.logo = new Logo();
        this.navigation = new Navigation();
        this.panel = new Panel();
        this.applyStyles();
        this.render();
    }

    private void render() {
        this.addChildren(this.logo, this.navigation, this.panel);
    }

    private void applyStyles() {
        this.setDirection(Direction.X_DIRECTION);
        this.setAlign(Align.SPACE_BETWEEN, Align.CENTER);
        this.setPadding(0.0, 24.0d);
        this.addInlineStyles("-fx-background-color: #eaeef6;");
        this.applyLogoStyles();
        this.applyNavigationStyles();
    }

    private void applyLogoStyles() {
        this.logo.bindHeightPercent(this, 80);
    }

    private void applyNavigationStyles() {
        this.navigation.bindHeightPercent(this, 100);
    }
}