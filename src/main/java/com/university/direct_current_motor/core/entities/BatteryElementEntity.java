package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class BatteryElementEntity
        extends AbstractCircuitElementEntity
        implements Voltable, Resistable {

    private double voltage;
    private final double internalResistance;

    public BatteryElementEntity(
            final Long id,
            final double voltage
    ) {
        this(id, voltage, Resistable.ZERO_RESISTANCE);
    }

    public BatteryElementEntity(
            final Long id,
            final double voltage,
            final double internalResistance
    ) {
        super(
                Objects.requireNonNull(id),
                AbstractCircuitElementEntity.CircuitElementType.BATTERY
        );
        this.voltage = voltage;
        this.internalResistance = internalResistance;
    }

    public BatteryElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final double voltage
    ) {
        this(id, createdAt,  updatedAt, voltage, Resistable.ZERO_RESISTANCE);
    }

    public BatteryElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final double voltage,
            final double internalResistance
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt,
                AbstractCircuitElementEntity.CircuitElementType.BATTERY
        );
        this.voltage = voltage;
        this.internalResistance = internalResistance;
    }

    @Override
    public double getVoltage() {
        return this.voltage;
    }

    public void setVoltage(final double voltage) {
        this.voltage = voltage;
    }

    @Override
    public double getResistance() {
        return this.internalResistance;
    }

    @Override
    protected List<AbstractCircuitElementEntity.PinDefinition> definePins() {
        return List.of(
                new AbstractCircuitElementEntity.PinDefinition(Pin.PinType.POSITIVE),
                new AbstractCircuitElementEntity.PinDefinition(Pin.PinType.NEGATIVE)
        );
    }

}
