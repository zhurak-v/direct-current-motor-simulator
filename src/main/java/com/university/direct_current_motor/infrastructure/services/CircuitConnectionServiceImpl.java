package com.university.direct_current_motor.infrastructure.services;

import com.university.direct_current_motor.core.entities.AbstractCircuitElementEntity;
import com.university.direct_current_motor.core.entities.CircuitEntity;
import com.university.direct_current_motor.core.values.Pin;

import java.util.Objects;
import java.util.Optional;

public final class CircuitConnectionServiceImpl {

    public CircuitConnectionServiceImpl() {}

    public <E extends AbstractCircuitElementEntity> void addElement(
            final CircuitEntity circuit,
            final E element
    ) {
        Objects.requireNonNull(circuit);
        Objects.requireNonNull(element);

        circuit.addElement(element);
    }

    public <E extends AbstractCircuitElementEntity> void removeElement(
            final CircuitEntity circuit,
            final E element
    ) {
        Objects.requireNonNull(circuit);
        Objects.requireNonNull(element);

        circuit.removeElement(element);
    }

    public <E1 extends AbstractCircuitElementEntity,
            E2 extends AbstractCircuitElementEntity>
    void connect(
        final E1 elementA,
        final int pinIndexA,
        final E2 elementB,
        final int pinIndexB
    ) {
        Objects.requireNonNull(elementA);
        Objects.requireNonNull(elementB);

        final Pin pinA = this.getPinOrThrow(elementA, pinIndexA);
        final Pin pinB = this.getPinOrThrow(elementB, pinIndexB);

        validateConnection(pinA, pinB);
        pinA.connect(pinB);
    }


    public <E1 extends AbstractCircuitElementEntity,
            E2 extends AbstractCircuitElementEntity>
    void connectByPinType(
            final E1 elementA,
            final Pin.PinType typeA,
            final E2 elementB,
            final Pin.PinType typeB
    ) {
        Objects.requireNonNull(elementA);
        Objects.requireNonNull(typeA);
        Objects.requireNonNull(elementB);
        Objects.requireNonNull(typeB);

        final Pin pinA = this.findFirstPinByType(elementA, typeA).orElseThrow(
                () -> new IllegalArgumentException("Element " + elementA.getId()
                        + " doesn`t have pin type of " + typeA));

        final Pin pinB = findFirstPinByType(elementB, typeB).orElseThrow(
                () -> new IllegalArgumentException("Element " + elementB.getId()
                        + " doesn`t have pin type of " + typeB));

        validateConnection(pinA, pinB);
        pinA.connect(pinB);
    }

    public void disconnect(
            final Pin pinA,
            final Pin pinB
    ) {
        Objects.requireNonNull(pinA);
        Objects.requireNonNull(pinB);

        pinA.getConnections().remove(pinB);
        pinB.getConnections().remove(pinA);
    }

    private <E extends AbstractCircuitElementEntity> Pin getPinOrThrow(
            final E element,
            final int index
    ) {
        Objects.requireNonNull(element);

        if (index < 0 || index >= element.getPins().size()) {
            throw new IndexOutOfBoundsException("Invalid pin index " + index +
                    " for element " + element.getClass().getSimpleName());
        }
        return element.getPins().get(index);
    }

    private <E extends AbstractCircuitElementEntity> Optional<Pin> findFirstPinByType(
            final E element,
            final Pin.PinType type
    ) {
        Objects.requireNonNull(element);
        Objects.requireNonNull(type);

        return element.getPins().stream()
                .filter(p -> p.getType() == type)
                .findFirst();
    }

    private void validateConnection(final Pin a, final Pin b) {
        Objects.requireNonNull(a);
        if (a.getOwner().equals(b.getOwner())) {
            throw new IllegalStateException("Short circuit: it is impossible to connect" +
                    " the pins of one element " + a.getOwner().getId());
        }
    }

}
