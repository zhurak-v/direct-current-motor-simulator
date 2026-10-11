package com.university.direct_current_motor.adapters.desktop.configurations;

import com.university.direct_current_motor.adapters.desktop.feautures.components.TheoryScreen;
import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.configurations.BeanConfiguration;

@Configuration(name = "theory-screen")
public final class TheoryScreenConfiguration extends BeanConfiguration {

    public TheoryScreenConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(TheoryScreen.class)
                .setFactory(TheoryScreen::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}