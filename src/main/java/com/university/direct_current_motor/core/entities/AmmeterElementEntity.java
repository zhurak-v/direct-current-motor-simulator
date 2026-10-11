package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class AmmeterElementEntity
        extends AbstractCircuitElementEntity
        implements Resistable, Ammeterable {

    private final double resistance;
    private double measuredCurrent;

    public AmmeterElementEntity(
            final Long id
    ) {
        this(id, Resistable.ZERO_RESISTANCE);
    }

    public AmmeterElementEntity(
            final Long id,
            final double resistance
    ) {
        super(
                Objects.requireNonNull(id),
                CircuitElementType.AMMETER
        );
        this.resistance = resistance;
        this.measuredCurrent = Ammeterable.ZERO_CURRENT;
    }

    public AmmeterElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        this(id, createdAt, updatedAt, Resistable.ZERO_RESISTANCE);
    }

    public AmmeterElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final double resistance
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt,
                CircuitElementType.AMMETER
        );
        this.resistance = resistance;
        this.measuredCurrent = Ammeterable.ZERO_CURRENT;
    }

    @Override
    public double getResistance() {
        return this.resistance;
    }

    @Override
    protected List<PinDefinition> definePins() {
        return List.of(
                new PinDefinition(Pin.PinType.POSITIVE),
                new PinDefinition(Pin.PinType.NEGATIVE)
        );
    }

    @Override
    public double getCurrent() {
        return this.measuredCurrent;
    }

    public void setCurrent(final double current) {
        this.measuredCurrent = current;
    }

}