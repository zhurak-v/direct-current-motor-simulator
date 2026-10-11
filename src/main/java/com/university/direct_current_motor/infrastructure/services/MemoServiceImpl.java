package com.university.direct_current_motor.infrastructure.services;

import com.university.direct_current_motor.core.ports.services.MemoService;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public final class MemoServiceImpl implements MemoService {

    private record CacheEntry(Object value, Instant expiresAt) {

        private boolean isExpired() {
            return Objects.nonNull(this.expiresAt)
                    && Instant.now().isAfter(this.expiresAt);
        }

    }

    private static final class Holder {

        private static final MemoService INSTANCE;

        static {
            INSTANCE = new MemoServiceImpl();
        }

        private Holder() {
            throw new UnsupportedOperationException();
        }

    }

    public static MemoService getMemoService() {
        return Holder.INSTANCE;
    }

    private final Map<String, CacheEntry> cache;
    private final ScheduledExecutorService scheduler;

    private MemoServiceImpl() {
        this.cache = new ConcurrentHashMap<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor(runnable -> {
            final Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });

        this.scheduler.scheduleAtFixedRate(this::cleanup, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T memoize(final String key, final Supplier<T> supplier) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(supplier);

        final CacheEntry entry = this.cache.computeIfAbsent(key, k -> new CacheEntry(supplier.get(), null));

        return (T) entry.value();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T memoize(final String key, final Duration ttl, final Supplier<T> supplier) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(ttl);
        Objects.requireNonNull(supplier);

        final CacheEntry entry = this.cache.compute(key, (k, old) -> (Objects.isNull(old) || old.isExpired())
                ? new CacheEntry(supplier.get(), Instant.now().plus(ttl))
                : old);

        return (T) entry.value();
    }

    private void cleanup() {
        if (this.cache.isEmpty()) {
            return;
        }
        this.cache.values().removeIf(CacheEntry::isExpired);
    }

    public void shutdown() {
        this.scheduler.shutdown();
    }

}