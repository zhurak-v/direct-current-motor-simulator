package com.university.direct_current_motor.infrastructure.configurations;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.ports.services.Router;
import com.university.direct_current_motor.infrastructure.services.RouterImpl;

@Configuration
public final class RouterConfiguration extends BeanConfiguration {

    public RouterConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(Router.class)
                .setFactory(RouterImpl::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}
