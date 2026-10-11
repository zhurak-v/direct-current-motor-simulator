package com.university.direct_current_motor.adapters.desktop.configurations;

import com.university.direct_current_motor.adapters.desktop.common.components.Root;
import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.configurations.BeanConfiguration;

@Configuration
public final class RootConfiguration extends BeanConfiguration {

    public RootConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(Root.class)
                .setFactory(Root::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}
