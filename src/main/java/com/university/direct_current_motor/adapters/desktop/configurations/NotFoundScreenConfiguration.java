package com.university.direct_current_motor.adapters.desktop.configurations;

import com.university.direct_current_motor.adapters.desktop.feautures.components.NotFoundScreen;
import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.configurations.BeanConfiguration;

@Configuration(name = "not-found-screen")
public final class NotFoundScreenConfiguration extends BeanConfiguration {

    public NotFoundScreenConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(NotFoundScreen.class)
                .setFactory(NotFoundScreen::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}