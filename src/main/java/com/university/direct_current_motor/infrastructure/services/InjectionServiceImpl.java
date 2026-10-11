package com.university.direct_current_motor.infrastructure.services;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.ports.services.MemoService;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class InjectionServiceImpl implements InjectionService {

    private static class Holder {

        private static final InjectionService INSTANCE;

        static {
            INSTANCE = new InjectionServiceImpl();
        }

        private Holder() {
            throw new UnsupportedOperationException();
        }

    }

    public static InjectionService getInjectionService() {
        return Holder.INSTANCE;
    }

    private static final class BeanStore<T> {

        private static final String DEFAULT_NAME;

        static {
            DEFAULT_NAME = Configuration.DEFAULT_NAME;
        }

        private record BeanDefinition<T>(Supplier<T> factory, Scope scope) {

            private BeanDefinition(
                    final Supplier<T> factory,
                    final Scope scope
            ) {
                this.factory = Objects.requireNonNull(factory);
                this.scope = Objects.requireNonNull(scope);
            }

        }

        private final Map<String, BeanDefinition<T>> store;

        public BeanStore() {
            this.store = new ConcurrentHashMap<>();
        }

        private void register(final String name, final BeanDefinition<T> definition) {
            this.validateRegistration(name);
            this.store.put(name, definition);
        }

        private void validateRegistration(final String name) {
            Objects.requireNonNull(name);

            if (this.store.isEmpty()) {
                return;
            }

            if (this.store.containsKey(name)) {
                throw new IllegalArgumentException(
                        String.format("Registration failed: Bean with name '%s' is already registered.", name)
                );
            }

            if (name.equals(DEFAULT_NAME)) {
                throw new IllegalArgumentException(
                        "Registration failed: Cannot register DEFAULT_NAME when other beans exist."
                );
            }

            if (this.store.containsKey(DEFAULT_NAME)) {
                throw new IllegalArgumentException(
                        String.format("Registration failed: Store with DEFAULT_NAME cannot accept '%s'.", name)
                );
            }
        }

        private BeanDefinition<T> getBeanDefinition(final String name) {
            final BeanDefinition<T> definition = this.store.get(Objects.requireNonNull(name));
            if (Objects.isNull(definition)) {
                throw new IllegalArgumentException(String.format("Bean not found: '%s'.", name));
            }
            return definition;
        }

        private BeanDefinition<T> getBeanDefinition() {
            final int size = this.store.size();

            if (size == 0) {
                throw new IllegalStateException("No beans registered in this store.");
            }

            if (size == 1) {
                return this.store.values().iterator().next();
            }

            throw new IllegalStateException(
                    String.format(
                            "Ambiguous bean resolution: Found %d beans. " +
                                    "You must specify a name explicitly using getBean(Class<?> clazz, String name) " +
                                    "because it is unknown which one to return.", size
                    )
            );
        }

        private String resolveName() {
            final int size = this.store.size();

            if (size == 1) {
                return this.store.keySet().iterator().next();
            }

            throw new IllegalStateException("Ambiguous bean resolution: specify a name explicitly.");
        }

    }

    private final Map<Class<?>, BeanStore<Object>> beans;
    private final MemoService memoService;

    public InjectionServiceImpl() {
        this.beans = new ConcurrentHashMap<>();
        this.memoService = MemoService.getMemoService();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void registerBean(
            final Class<?> clazz,
            final String name,
            final Supplier<?> factory,
            final InjectionService.Scope scope
    ) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(name);
        Objects.requireNonNull(factory);
        Objects.requireNonNull(scope);

        final BeanStore.BeanDefinition<Object> definition = new BeanStore.BeanDefinition<>(
                (Supplier<Object>) factory,
                scope
        );

        final BeanStore<Object> store = this.beans.computeIfAbsent(
                clazz,
                k -> new BeanStore<>()
        );

        store.register(name, definition);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<?> clazz, final String name) {
        final BeanStore<Object> store = this.getStoreOrThrow(clazz);
        final BeanStore.BeanDefinition<Object> definition = store.getBeanDefinition(name);

        return (T) this.resolveInstance(clazz, name, definition);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(final Class<?> clazz) {
        final BeanStore<Object> store = this.getStoreOrThrow(clazz);
        final BeanStore.BeanDefinition<Object> definition = store.getBeanDefinition();
        final String name = store.resolveName();

        return (T) this.resolveInstance(clazz, name, definition);
    }

    private BeanStore<Object> getStoreOrThrow(final Class<?> clazz) {
        final BeanStore<Object> store = this.beans.get(Objects.requireNonNull(clazz));
        if (Objects.isNull(store)) {
            throw new IllegalArgumentException("No beans registered for type: " + clazz.getName());
        }
        return store;
    }

    private Object resolveInstance(
            final Class<?> clazz,
            final String name,
            final BeanStore.BeanDefinition<Object> definition
    ) {
        final Object instance = switch (definition.scope()) {
            case SINGLETON -> {
                final String cacheKey = clazz.getName() + ":" + name;
                yield this.memoService.memoize(cacheKey, definition.factory());
            }
            case PROTOTYPE -> definition.factory().get();
        };

        return this.validateInstance(clazz, name, instance);
    }

    private Object validateInstance(final Class<?> expectedType, final String name, final Object instance) {
        if (Objects.isNull(instance)) {
            throw new IllegalStateException("Factory returned null for bean: " + name);
        }

        if (!expectedType.isAssignableFrom(instance.getClass())) {
            throw new IllegalArgumentException(
                    String.format("%s is not assignable to %s", instance.getClass().getName(), expectedType.getName())
            );
        }

        return instance;
    }

}