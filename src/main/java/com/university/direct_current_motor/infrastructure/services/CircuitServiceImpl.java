package com.university.direct_current_motor.infrastructure.services;

import com.university.direct_current_motor.core.entities.*;
import com.university.direct_current_motor.core.ports.services.CircuitService;

import java.util.Objects;

public class CircuitServiceImpl implements CircuitService {

    private final CircuitEntity circuit;
    private final CircuitCalculatorImpl calculator;

    public CircuitServiceImpl(final CircuitEntity circuit) {
        this.circuit = circuit;
        this.calculator = new CircuitCalculatorImpl(circuit);
        this.calculator.calculate();
    }


    @Override
    public void changeRheostatResistance(final Long id, final double resistance) {
        final RheostatElementEntity rheostat = (RheostatElementEntity) this.findElementById(id);
        if (rheostat.getType() != AbstractCircuitElementEntity.CircuitElementType.RHEOSTAT) {
            throw new IllegalArgumentException("The element is not a Rheostat");
        }
        rheostat.setResistance(resistance);
        this.calculator.calculate();
    }

    @Override
    public void changeBatteryVoltage(final Long id, final double voltage) {
        final BatteryElementEntity batteryElementEntity = (BatteryElementEntity) this.findElementById(id);
        if (batteryElementEntity.getType() != AbstractCircuitElementEntity.CircuitElementType.BATTERY) {
            throw new IllegalArgumentException("The element is not a Battery");
        }
        batteryElementEntity.setVoltage(voltage);
        this.calculator.calculate();
    }

    @Override
    public void toggleSwitch(final Long id) {
        final SwitchElementEntity switchElement = (SwitchElementEntity) this.findElementById(id);
        if (switchElement.getType() != AbstractCircuitElementEntity.CircuitElementType.SWITCH) {
            throw new IllegalArgumentException("The element is not a Switch");
        }
        switchElement.toggle();
        this.calculator.calculate();
    }

    @Override
    public void openSwitch(final Long id) {
        final SwitchElementEntity switchElement = (SwitchElementEntity) this.findElementById(id);
        if (switchElement.getType() != AbstractCircuitElementEntity.CircuitElementType.SWITCH) {
            throw new IllegalArgumentException("The element is not a Switch");
        }
        switchElement.open();
        this.calculator.calculate();
    }

    @Override
    public void closeSwitch(final Long id) {
        final SwitchElementEntity switchElement = (SwitchElementEntity) this.findElementById(id);
        if (switchElement.getType() != AbstractCircuitElementEntity.CircuitElementType.SWITCH) {
            throw new IllegalArgumentException("The element is not a Switch");
        }
        switchElement.close();
        this.calculator.calculate();
    }

    @Override
    public void nextTick() {
        this.calculator.calculate();
    }

    @Override
    public RheostatElementEntity findRheostatById(final Long id) {
        return this.findElementByIdAndType(id, RheostatElementEntity.class);
    }

    @Override
    public BatteryElementEntity findBatteryById(final Long id) {
        return this.findElementByIdAndType(id, BatteryElementEntity.class);
    }

    @Override
    public SwitchElementEntity findSwitchById(final Long id) {
        return this.findElementByIdAndType(id, SwitchElementEntity.class);
    }

    @Override
    public WireElementEntity findWireById(final Long id) {
        return this.findElementByIdAndType(id, WireElementEntity.class);
    }

    @Override
    public AmmeterElementEntity findAmmeterById(final Long id) {
        return this.findElementByIdAndType(id, AmmeterElementEntity.class);
    }

    @Override
    public VoltmeterElementEntity findVoltmeterById(final Long id) {
        return this.findElementByIdAndType(id, VoltmeterElementEntity.class);
    }

    @Override
    public InductorElementEntity findInductorById(final Long id) {
        return this.findElementByIdAndType(id, InductorElementEntity.class);
    }

    @Override
    public EngineElementEntity findEngineById(final Long id) {
        return this.findElementByIdAndType(id, EngineElementEntity.class);
    }

    private <T extends AbstractCircuitElementEntity> T findElementByIdAndType(
            final Long id,
            final Class<T> type
    ) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(type);

        final AbstractCircuitElementEntity element = this.findElementById(id);

        if (!type.isInstance(element)) {
            throw new IllegalArgumentException("Element with ID " + id + " is not of type " + type.getSimpleName());
        }

        return type.cast(element);
    }

    private AbstractCircuitElementEntity findElementById(final Long id) {
        Objects.requireNonNull(id);
        return this.circuit.getElements()
                .stream()
                .filter(element -> element.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Element with ID " + id + " not found"));
    }

}
