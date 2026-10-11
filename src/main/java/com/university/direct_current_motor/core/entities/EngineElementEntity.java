package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class EngineElementEntity
        extends AbstractCircuitElementEntity
        implements Resistable {

    private final double armatureResistance;
    private final double cePh;
    private final double jInertia;

    private double speed = 0.0;
    private double loadTorque = 0.5;
    private double current = 0.0;

    public EngineElementEntity(
            final Long id,
            final double armatureResistance,
            final double cePh,
            final double jInertia
    ) {
        super(Objects.requireNonNull(id), CircuitElementType.ENGINE);
        this.armatureResistance = armatureResistance;
        this.cePh = cePh;
        this.jInertia = jInertia;
    }

    public void step(final double deltaTime, final double actualCurrent) {
        this.current = actualCurrent;

        double E = this.cePh * this.speed;

        double motorTorque = 0.0;
        if (this.speed > 0) {
            double pElectromagnetic = E * this.current;
            motorTorque = 9550.0 * (pElectromagnetic / 1000.0) / this.speed;
        } else if (this.current > 0) {
            double cmPh = 9.55 * this.cePh;
            motorTorque = cmPh * this.current;
        }

        double netTorque = motorTorque - this.loadTorque;
        double acceleration = netTorque / this.jInertia;

        this.speed += acceleration * deltaTime;
        if (this.speed < 0) {
            this.speed = 0;
        }
    }

    @Override
    public double getResistance() {
        if (Math.abs(this.current) < 1e-5) {
            return this.armatureResistance;
        }
        double E = this.cePh * this.speed;
        return this.armatureResistance + (E / this.current);
    }

    public double getArmatureResistance() {
        return this.armatureResistance;
    }

    @Override
    protected List<PinDefinition> definePins() {
        return List.of(
                new PinDefinition(Pin.PinType.PASSIVE),
                new PinDefinition(Pin.PinType.PASSIVE)
        );
    }

    public double getSpeed() {
        return this.speed;
    }
    public double getE() {
        return this.cePh * this.speed;
    }

    public double getLoadTorque() {
        return this.loadTorque;
    }

    public void setLoadTorque(double loadTorque) {
        this.loadTorque = loadTorque;
    }

    public double getMotorTorque() {
        double E = getE();
        return this.speed > 0
                ? (9550.0 * (E * this.current / 1000.0) / this.speed)
                : (9.55 * this.cePh * this.current);
    }
}