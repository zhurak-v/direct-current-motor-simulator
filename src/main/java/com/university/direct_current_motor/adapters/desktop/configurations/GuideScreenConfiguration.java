package com.university.direct_current_motor.adapters.desktop.configurations;

import com.university.direct_current_motor.adapters.desktop.feautures.components.GuideScreen;
import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.configurations.BeanConfiguration;

@Configuration(name = "guide-screen")
public final class GuideScreenConfiguration extends BeanConfiguration {

    public GuideScreenConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(GuideScreen.class)
                .setFactory(GuideScreen::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}