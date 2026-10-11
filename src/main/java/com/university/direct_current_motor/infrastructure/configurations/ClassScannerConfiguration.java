package com.university.direct_current_motor.infrastructure.configurations;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.ClassScanner;
import com.university.direct_current_motor.core.ports.services.InjectionService;

@Configuration
public final class ClassScannerConfiguration extends BeanConfiguration {

    public ClassScannerConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {
        builder
                .setType(ClassScanner.class)
                .setFactory(ClassScanner::getClassScanner)
                .setScope(InjectionService.Scope.SINGLETON);
    }

}
