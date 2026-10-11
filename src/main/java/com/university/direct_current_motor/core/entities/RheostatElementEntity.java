package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class RheostatElementEntity
        extends AbstractCircuitElementEntity
        implements Resistable {

    private double resistance;

    public RheostatElementEntity(
            final Long id,
            final double resistance
    ) {
        super(
                Objects.requireNonNull(id),
                AbstractCircuitElementEntity.CircuitElementType.RHEOSTAT
        );
        this.resistance = resistance;
    }

    public RheostatElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final double resistance
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt,
                AbstractCircuitElementEntity.CircuitElementType.RHEOSTAT
        );
        this.resistance = resistance;
    }

    @Override
    public double getResistance() {
        return this.resistance;
    }

    public void setResistance(final double resistance) {
        this.resistance = resistance;
    }

    @Override
    protected List<AbstractCircuitElementEntity.PinDefinition> definePins() {
        return List.of(
                new AbstractCircuitElementEntity.PinDefinition(Pin.PinType.PASSIVE),
                new AbstractCircuitElementEntity.PinDefinition(Pin.PinType.PASSIVE)
        );
    }


}
