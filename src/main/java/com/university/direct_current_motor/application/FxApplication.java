package com.university.direct_current_motor.application;

import com.university.direct_current_motor.adapters.desktop.common.components.Root;
import com.university.direct_current_motor.core.ports.services.ClassScanner;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.ports.services.Router;
import com.university.direct_current_motor.core.ports.services.ScreenRegistrar;
import com.university.direct_current_motor.core.values.Resolution;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public final class FxApplication extends javafx.application.Application {

    public FxApplication() {
        super();
    }

    @Override
    public void start(final Stage stage) {
        this.registerScreen();
        this.navigateToHomeScreen();
        this.setupStage(stage);
    }

    private void setupStage(final Stage stage) {
        final Scene scene = this.createScene();
        stage.setTitle("Генератор постійного струму");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(scene.getWidth() + 16);
        stage.setMinHeight(scene.getHeight() + 40);
        stage.show();
        stage.getIcons().add(
            new Image(Objects.requireNonNull(
                FxApplication.class.getResourceAsStream("/icons/university-logo.png"))
            )
        );

    }

    private Scene createScene() {
        final InjectionService injectionService = InjectionService.getInjectionService();
        final Resolution resolution = injectionService.getBean(Resolution.class);
        final Root root = injectionService.getBean(Root.class);
        return new Scene(root.getNode(), resolution.getWidth(), resolution.getHeight());
    }

    private void registerScreen() {
        final InjectionService injectionService = InjectionService.getInjectionService();
        final ClassScanner classScanner = injectionService.getBean(ClassScanner.class);
        ScreenRegistrar.register();
        classScanner.scan();
    }

    private void navigateToHomeScreen() {
        final InjectionService injectionService = InjectionService.getInjectionService();
        final Router router = injectionService.getBean(Router.class);
        router.navigate("home-screen");
    }

}
