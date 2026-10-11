package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;
import java.util.List;
import java.util.Objects;

public final class AsynchronousBrakeEntity
        extends AbstractCircuitElementEntity
        implements Resistable {

    private final double totalStatorResistance;
    private final double brakeCoefficient;
    private double current = 0.0;

    public AsynchronousBrakeEntity(
            final Long id,
            final double totalStatorResistance,
            final double brakeCoefficient
    ) {
        super(Objects.requireNonNull(id), CircuitElementType.ASYNCHRONOUS_BRAKE);
        this.totalStatorResistance = totalStatorResistance;
        this.brakeCoefficient = brakeCoefficient;
    }

    public double calculateTorque(final double speed) {
        return this.brakeCoefficient * Math.pow(this.current, 2) * speed;
    }

    public void setCurrent(final double current) {
        this.current = current;
    }

    @Override
    public double getResistance() {
        return this.totalStatorResistance;
    }

    @Override
    protected List<PinDefinition> definePins() {
        return List.of(
                new PinDefinition(Pin.PinType.PASSIVE),
                new PinDefinition(Pin.PinType.PASSIVE)
        );
    }
}