package com.university.direct_current_motor.infrastructure.configurations;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.values.Resolution;

@Configuration
public final class ResolutionConfiguration extends BeanConfiguration {

    public ResolutionConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(Resolution.class)
                .setFactory(() -> Resolution.R_1440x810)
                .setScope(InjectionService.Scope.SINGLETON);
    }
}
