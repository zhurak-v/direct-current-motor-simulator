package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class InductorElementEntity
        extends AbstractCircuitElementEntity
        implements Inductable, Resistable {

    private final double activeResistance;
    private final double inductance;

    private double lastCurrent = 0.0;
    private double current = 0.0;
    private double deltaTime = 0.01;

    public InductorElementEntity(
            final Long id,
            final double activeResistance,
            final double inductance
    ) {
        super(Objects.requireNonNull(id), CircuitElementType.INDUCTOR);
        this.activeResistance = activeResistance;
        this.inductance = inductance;
    }

    public void step(final double deltaTime, final double actualCurrent) {
        this.lastCurrent = this.current;
        this.current = actualCurrent;
        this.deltaTime = deltaTime;
    }

    public void setDeltaTime(final double deltaTime) {
        this.deltaTime = deltaTime;
    }

    @Override
    public double getResistance() {
        return this.activeResistance + (this.inductance / this.deltaTime);
    }

    @Override
    public double getInductance() {
        return this.inductance;
    }

    @Override
    protected List<PinDefinition> definePins() {
        return List.of(
                new PinDefinition(Pin.PinType.PASSIVE),
                new PinDefinition(Pin.PinType.PASSIVE)
        );
    }
}