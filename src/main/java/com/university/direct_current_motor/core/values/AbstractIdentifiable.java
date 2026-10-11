package com.university.direct_current_motor.core.values;


import java.util.Objects;

public abstract class AbstractIdentifiable<T> {

    private final T id;

    protected AbstractIdentifiable(
            final T id
    ) {
        Objects.requireNonNull(id);
        this.id = id;
    }

    public final T getId() {
        return this.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (Objects.isNull(o) || this.getClass() != o.getClass()) {
            return false;
        }

        final AbstractIdentifiable<?> identifiable = (AbstractIdentifiable<?>) o;

        return Objects.equals(this.id, identifiable.getId());
    }

}
