package com.university.direct_current_motor.core.entities;

import com.university.direct_current_motor.core.values.Pin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractCircuitElementEntity
        extends AbstractEntity<Long> {

    public enum CircuitElementType {
        WIRE,
        BATTERY,
        RHEOSTAT,
        AMMETER,
        VOLTMETER,
        INDUCTOR,
        SWITCH,
        ENGINE,
        ASYNCHRONOUS_BRAKE
    }

    public record PinDefinition(Pin.PinType type) {}

    private final CircuitElementType type;

    private final List<Pin> pins;

    protected AbstractCircuitElementEntity(
            final Long id,
            final CircuitElementType type
    ) {
        super(Objects.requireNonNull(id));
        this.type = Objects.requireNonNull(type);
        this.pins = new ArrayList<>();
        this.initPins();
    }

    protected AbstractCircuitElementEntity(
            final Long id,
            final OffsetDateTime createdAt,
            final OffsetDateTime updatedAt,
            final CircuitElementType type
    ) {
        super(
                Objects.requireNonNull(id),
                Objects.requireNonNull(createdAt),
                updatedAt
        );
        this.type = Objects.requireNonNull(type);
        this.pins = new ArrayList<>();
        this.initPins();
    }

    protected abstract List<PinDefinition> definePins();

    private void initPins() {
        for (final PinDefinition definition : this.definePins()) {
            this.pins.add(new Pin(this, definition.type()));
        }
    }

    public final CircuitElementType getType() {
        return this.type;
    }

    public final List<Pin> getPins() {
        return Collections.unmodifiableList(this.pins);
    }


}
