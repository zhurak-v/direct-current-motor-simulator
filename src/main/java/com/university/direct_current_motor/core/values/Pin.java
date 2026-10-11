package com.university.direct_current_motor.core.values;

import com.university.direct_current_motor.core.entities.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public final class Pin {

    public enum PinType {
        PASSIVE,
        POSITIVE,
        NEGATIVE
    }

    private final AbstractCircuitElementEntity owner;

    private final Set<Pin> connections;

    private final PinType type;

    public Pin(
            final AbstractCircuitElementEntity owner,
            final PinType type
    ) {
        this.owner = Objects.requireNonNull(owner);
        this.connections = new HashSet<>();
        this.type = type;
    }

    public void connect(final Pin other) {
        Objects.requireNonNull(other);

        this.connections.add(other);
        other.connections.add(this);
    }

    public void disconnect(final Pin other) {
        this.connections.remove(other);
        other.connections.remove(this);
    }

    public AbstractCircuitElementEntity getOwner() {
        return this.owner;
    }

    public Set<Pin> getConnections() {
        return this.connections;
    }

    public PinType getType() {
        return this.type;
    }

}
