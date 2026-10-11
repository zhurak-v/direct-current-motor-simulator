package com.university.direct_current_motor.core.entities;

import java.time.OffsetDateTime;
import java.util.*;

public final class CircuitEntity extends AbstractEntity<Long> {

    private final Set<AbstractCircuitElementEntity> elements;

    public CircuitEntity(
            final Long id
    ) {
        super(Objects.requireNonNull(id));
        this.elements = new HashSet<>();
    }

    public CircuitEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        this(id, createdAt, updatedAt, new HashSet<>());
    }

    public CircuitEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final Set<AbstractCircuitElementEntity> elements
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt
        );
        this.elements = elements;
    }


    public <T extends AbstractCircuitElementEntity> void addElement(final T element) {
        Objects.requireNonNull(element);

        if (this.elements.contains(element)) {
            throw new IllegalArgumentException("Element already exists in circuit");
        }

        this.elements.add(element);
    }

    public <T extends AbstractCircuitElementEntity> void addElements(final T... elements) {
        Objects.requireNonNull(elements);
        for (final T element : elements) {
            this.addElement(element);
        }
    }

    public void removeElement(final AbstractCircuitElementEntity element) {
        Objects.requireNonNull(element);

        if (!this.elements.contains(element)) {
            return;
        }

        element.getPins()
                .forEach(pin -> new HashSet<>(pin.getConnections())
                .forEach(pin::disconnect));

        this.elements.remove(element);
    }

    public Set<AbstractCircuitElementEntity> getElements() {
        return Collections.unmodifiableSet(this.elements);
    }

    public void clear() {
        this.elements.forEach(this::removeElement);
        this.elements.clear();
    }

}
