package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public final class SwitchElementEntity
        extends AbstractCircuitElementEntity
        implements Switchable, Resistable {

    private boolean isOpen;

    public SwitchElementEntity(
            final Long id
    ) {
        super(
                Objects.requireNonNull(id),
                CircuitElementType.SWITCH
        );
        this.isOpen = true;
    }

    public SwitchElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt,
                CircuitElementType.SWITCH
        );
        this.isOpen = true;
    }

    @Override
    public boolean isOpen() {
        return this.isOpen;
    }

    @Override
    public double getResistance() {
        return this.isOpen
                ? Resistable.MAX_RESISTANCE
                : Resistable.GROUND_CONDUCTANCE;
    }

    public void open() {
        this.isOpen = true;
    }

    public void close() {
        this.isOpen = false;
    }

    public void toggle() {
        this.isOpen = !this.isOpen;
    }

    @Override
    protected List<PinDefinition> definePins() {
        return List.of(
                new PinDefinition(Pin.PinType.PASSIVE),
                new PinDefinition(Pin.PinType.PASSIVE)
        );
    }
}