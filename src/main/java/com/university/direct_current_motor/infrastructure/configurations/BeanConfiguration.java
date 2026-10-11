package com.university.direct_current_motor.infrastructure.configurations;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public abstract class BeanConfiguration {

    protected static final class BeanDefinitionBuilder {

        private Class<?> clazz;
        private Supplier<?> factory;
        private InjectionService.Scope scope;

        private BeanDefinitionBuilder() {}

        public BeanDefinitionBuilder setType(final Class<?> clazz) {
            this.clazz = Objects.requireNonNull(clazz);
            return this;
        }

        public BeanDefinitionBuilder setFactory(final Supplier<?> factory) {
            this.factory = Objects.requireNonNull(factory);
            return this;
        }

        public BeanDefinitionBuilder setScope(final InjectionService.Scope scope) {
            this.scope = Objects.requireNonNull(scope);
            return this;
        }

        private BeanDefinition build() {
            return new BeanDefinition(
                    Objects.requireNonNull(this.clazz, "Type must be specified"),
                    Objects.requireNonNull(this.factory, "Factory must be specified"),
                    Objects.requireNonNull(this.scope, "Scope must be specified")
            );
        }
    }

    private static final Logger LOGGER;

    static {
        LOGGER = LoggerFactory.getLogger(BeanConfiguration.class);
    }

    private record BeanDefinition(
            Class<?> clazz,
            Supplier<?> factory,
            InjectionService.Scope scope
    ) {}

    private final InjectionService injectionService;
    private final AtomicBoolean defined;
    private final String beanName;

    protected BeanConfiguration() {
        this.injectionService = InjectionService.getInjectionService();
        this.defined = new AtomicBoolean(false);
        final Configuration annotation = this.getClass().getAnnotation(Configuration.class);
        this.beanName = Objects.nonNull(annotation)
                ? annotation.name()
                : Configuration.DEFAULT_NAME;
    }

    public final void configure() {
        if (!this.defined.compareAndSet(false, true)) {
            return;
        }

        final BeanDefinitionBuilder builder = new BeanDefinitionBuilder();
        this.defineBean(builder);

        final BeanDefinition definition = builder.build();

        this.injectionService.registerBean(
                definition.clazz(),
                this.beanName,
                definition.factory(),
                definition.scope()
        );

        LOGGER.info(
                "Registered bean: name={}, type={}, scope={}",
                this.beanName,
                definition.clazz().getSimpleName(),
                definition.scope()
        );
    }

    protected abstract void defineBean(final BeanDefinitionBuilder builder);
}