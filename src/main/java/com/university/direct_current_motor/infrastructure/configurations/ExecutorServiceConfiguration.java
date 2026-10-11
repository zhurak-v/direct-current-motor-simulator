package com.university.direct_current_motor.infrastructure.configurations;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration(name = "forkJoinPool")
public final class ExecutorServiceConfiguration extends BeanConfiguration {

    public ExecutorServiceConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(ExecutorService.class)
                .setFactory(Executors::newWorkStealingPool)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}