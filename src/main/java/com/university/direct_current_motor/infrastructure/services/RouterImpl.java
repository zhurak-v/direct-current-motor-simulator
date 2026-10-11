package com.university.direct_current_motor.infrastructure.services;

import com.university.direct_current_motor.adapters.desktop.feautures.components.NotFoundScreen;
import com.university.direct_current_motor.adapters.desktop.common.components.Root;
import com.university.direct_current_motor.adapters.desktop.common.components.Screen;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.ports.services.Router;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class RouterImpl implements Router {

    private final Map<String, Screen> routes;
    private final InjectionService injectionService;

    public RouterImpl() {
        this.routes = new ConcurrentHashMap<>();
        this.injectionService = InjectionService.getInjectionService();
    }

    @Override
    public Screen navigate(final String screenName) {
        Objects.requireNonNull(screenName);
        final Screen screen = Objects.nonNull(this.routes.get(screenName))
                ? this.routes.get(screenName)
                : this.injectionService.getBean(NotFoundScreen.class);
        final Root root = this.injectionService.getBean(Root.class);
        root.setScreen(screen);
        return screen;
    }

    @Override
    public void registerScreen(
            final String screenName,
            final Screen screen
    ) {
        this.routes.putIfAbsent(
                Objects.requireNonNull(screenName),
                Objects.requireNonNull(screen)
        );
    }

    @Override
    public void setMainScreen(final Screen screen) {
        final Root root = this.injectionService.getBean(Root.class);
        root.setScreen(screen);
    }

}
