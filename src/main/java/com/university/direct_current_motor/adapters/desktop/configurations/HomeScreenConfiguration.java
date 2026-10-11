package com.university.direct_current_motor.adapters.desktop.configurations;

import com.university.direct_current_motor.adapters.desktop.feautures.components.HomeScreen;
import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.configurations.BeanConfiguration;

@Configuration(name = "home-screen")
public final class HomeScreenConfiguration extends BeanConfiguration {

    public HomeScreenConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(HomeScreen.class)
                .setFactory(HomeScreen::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}