package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class VoltmeterElementEntity
        extends AbstractCircuitElementEntity
        implements Voltable, Resistable {

    private final double resistance;
    private double measuredVoltage;

    public VoltmeterElementEntity(
            final Long id
    ) {
        super(
                Objects.requireNonNull(id),
                AbstractCircuitElementEntity.CircuitElementType.VOLTMETER
        );
        this.resistance = Resistable.MAX_RESISTANCE;
        this.measuredVoltage = Voltable.ZERO_VOLTAGE;
    }

    public VoltmeterElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt,
                AbstractCircuitElementEntity.CircuitElementType.VOLTMETER
        );
        this.resistance = Resistable.MAX_RESISTANCE;
        this.measuredVoltage = Voltable.ZERO_VOLTAGE;
    }

    @Override
    public double getVoltage() {
        return this.measuredVoltage;
    }

    public void setMeasuredVoltage(final double measuredVoltage) {
        this.measuredVoltage = measuredVoltage;
    }

    @Override
    public double getResistance() {
        return this.resistance;
    }

    @Override
    protected List<PinDefinition> definePins() {
        return List.of(
                new AbstractCircuitElementEntity.PinDefinition(Pin.PinType.POSITIVE),
                new AbstractCircuitElementEntity.PinDefinition(Pin.PinType.NEGATIVE)
        );
    }

}