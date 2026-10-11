package com.university.direct_current_motor.adapters.desktop.configurations;

import com.university.direct_current_motor.adapters.desktop.feautures.components.Header;
import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.configurations.BeanConfiguration;

@Configuration
public final class HeaderConfiguration extends BeanConfiguration {

    public HeaderConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(Header.class)
                .setFactory(Header::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}
