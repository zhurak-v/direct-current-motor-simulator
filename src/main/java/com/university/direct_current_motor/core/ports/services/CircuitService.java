package com.university.direct_current_motor.core.ports.services;

import com.university.direct_current_motor.core.entities.*;

public interface CircuitService {

    void changeRheostatResistance(
            final Long id, final double resistance
    );

    void changeBatteryVoltage(
            final Long id, final double voltage
    );

    void toggleSwitch(final Long id);

    void openSwitch(final Long id);

    void closeSwitch(final Long id);

    void nextTick();

    RheostatElementEntity findRheostatById(final Long id);

    BatteryElementEntity findBatteryById(final Long id);

    SwitchElementEntity findSwitchById(final Long id);

    WireElementEntity findWireById(final Long id);

    AmmeterElementEntity findAmmeterById(final Long id);

    VoltmeterElementEntity findVoltmeterById(final Long id);

    InductorElementEntity findInductorById(final Long id);

    EngineElementEntity findEngineById(final Long id);

}
