package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.AbstractIdentifiable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractEntity<T> extends AbstractIdentifiable<T> {

    private final OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    protected AbstractEntity(
            final T id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        super(Objects.requireNonNull(id));
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = updatedAt;
    }

    protected AbstractEntity(
            final T id
    ) {
        this(id, OffsetDateTime.now(ZoneOffset.UTC), null);
    }

    public final OffsetDateTime getCreatedAt() {
        return this.createdAt;
    }

    public final Optional<OffsetDateTime> getUpdatedAt() {
        return Optional.ofNullable(this.updatedAt);
    }

    public final void onUpdate() {
        this.updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }

    public final void setUpdatedAt(final OffsetDateTime updatedAt) {
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

}
