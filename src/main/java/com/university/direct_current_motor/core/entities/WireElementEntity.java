package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class WireElementEntity
        extends AbstractCircuitElementEntity
        implements Resistable {

    private final double resistance;

    public WireElementEntity(
            final Long id
    ) {
        this(id, Resistable.ZERO_RESISTANCE);
    }

    public WireElementEntity(
            final Long id,
            final double resistance
    ) {
        super(
                Objects.requireNonNull(id),
                CircuitElementType.WIRE
        );
        this.resistance = resistance;
    }

    public WireElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        this(id, createdAt, updatedAt, Resistable.ZERO_RESISTANCE);
    }

    public WireElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final double resistance
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt,
                CircuitElementType.WIRE
        );
        this.resistance = resistance;
    }

    @Override
    public double getResistance() {
        return this.resistance;
    }

    @Override
    protected List<PinDefinition> definePins() {
        return List.of(
                new PinDefinition(Pin.PinType.PASSIVE),
                new PinDefinition(Pin.PinType.PASSIVE)
        );
    }

}
