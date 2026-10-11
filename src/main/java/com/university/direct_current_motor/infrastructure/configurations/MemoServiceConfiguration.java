package com.university.direct_current_motor.infrastructure.configurations;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.ports.services.MemoService;

@Configuration
public final class MemoServiceConfiguration extends BeanConfiguration {

    public MemoServiceConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(MemoService.class)
                .setFactory(MemoService::getMemoService)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}
