package com.university.direct_current_motor.infrastructure.configurations;


import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.IdGenerator;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.services.AutoIncrementIdGeneratorImpl;

@Configuration(name = "autoIncrement")
public final class IdGeneratorConfiguration extends BeanConfiguration {

    public IdGeneratorConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(IdGenerator.class)
                .setFactory(AutoIncrementIdGeneratorImpl::new)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}
