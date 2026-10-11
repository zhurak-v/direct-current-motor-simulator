package com.university.direct_current_motor.adapters.desktop.configurations;

import com.university.direct_current_motor.adapters.desktop.feautures.components.AboutUsScreen;
import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.configurations.BeanConfiguration;

@Configuration(name = "about-us-screen")
public final class AboutUsScreenConfiguration extends BeanConfiguration {

    public AboutUsScreenConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(AboutUsScreen.class)
                .setFactory(AboutUsScreen::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}