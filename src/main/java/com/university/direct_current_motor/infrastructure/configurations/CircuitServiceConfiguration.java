package com.university.direct_current_motor.infrastructure.configurations;

import com.university.direct_current_motor.core.annotations.Configuration;
import com.university.direct_current_motor.core.entities.*;
import com.university.direct_current_motor.core.ports.services.CircuitService;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.infrastructure.services.CircuitConnectionServiceImpl;
import com.university.direct_current_motor.infrastructure.services.CircuitServiceImpl;

@Configuration
public final class CircuitServiceConfiguration extends BeanConfiguration {

    public CircuitServiceConfiguration() {
        super();
    }

    @Override
    protected void defineBean(final BeanDefinitionBuilder builder) {

        final CircuitEntity circuit = new CircuitEntity(0L);

        final BatteryElementEntity battery1 = new BatteryElementEntity(1L, 220.0);
        final WireElementEntity wire1 = new WireElementEntity(2L);
        final WireElementEntity wire2 = new WireElementEntity(3L);
        final AmmeterElementEntity ammeter1 = new AmmeterElementEntity(4L);
        final WireElementEntity wire3 = new WireElementEntity(5L);
        final RheostatElementEntity rheostat1 = new RheostatElementEntity(6L, 0.1);
        final WireElementEntity wire4 = new WireElementEntity(7L);
        final WireElementEntity wire5 = new WireElementEntity(8L);
        final InductorElementEntity inductor = new InductorElementEntity(9L, 550.0, 0.1);
        final WireElementEntity wire6 = new WireElementEntity(10L);
        final WireElementEntity wire7 = new WireElementEntity(11L);
        final WireElementEntity wire8 = new WireElementEntity(12L);

        final WireElementEntity wire9 = new WireElementEntity(13L);
        final WireElementEntity wire10 = new WireElementEntity(14L);
        final AmmeterElementEntity ammeter2 = new AmmeterElementEntity(15L);
        final WireElementEntity wire11 = new WireElementEntity(16L);
        final RheostatElementEntity rheostat2 = new RheostatElementEntity(17L, 0.1);
        final WireElementEntity wire12 = new WireElementEntity(18L);
        final WireElementEntity wire13 = new WireElementEntity(19L);
        final WireElementEntity wire14 = new WireElementEntity(20L);
        final EngineElementEntity motor = new EngineElementEntity(21L, 5.5, 0.14, 0.02);
        final WireElementEntity wire15  = new WireElementEntity(22L);
        final WireElementEntity wire16  = new WireElementEntity(23L);
        final WireElementEntity wire17 = new WireElementEntity(24L);
        final VoltmeterElementEntity voltmeter1 = new VoltmeterElementEntity(25L);


        final BatteryElementEntity battery2 = new BatteryElementEntity(26L, 220.0);
        final WireElementEntity wire18 = new WireElementEntity(27L);
        final WireElementEntity wire19 = new WireElementEntity(28L);
        final SwitchElementEntity switch1 = new SwitchElementEntity(29L);
        final WireElementEntity wire20 = new WireElementEntity(30L);
        final WireElementEntity wire21 = new WireElementEntity(31L);
        final RheostatElementEntity rheostat3 = new RheostatElementEntity(32L, 0.1);
        final WireElementEntity wire22 = new WireElementEntity(33L);
        final AmmeterElementEntity ammeter3 = new AmmeterElementEntity(34L);
        final WireElementEntity wire23 = new WireElementEntity(35L);
        final WireElementEntity wire24 = new WireElementEntity(36L);
        final AsynchronousBrakeEntity brake = new AsynchronousBrakeEntity(37L,12.0, 0.0001);
        final WireElementEntity wire25 = new WireElementEntity(38L);
        final WireElementEntity wire26 = new WireElementEntity(39L);
        final WireElementEntity wire27 = new WireElementEntity(40L);
        final WireElementEntity wire28 = new WireElementEntity(41L);
        final VoltmeterElementEntity voltmeter2 = new VoltmeterElementEntity(42L);

        circuit.addElements(
                battery1,
                wire1,
                wire2,
                ammeter1,
                wire3,
                rheostat1,
                wire4,
                wire5,
                inductor,
                wire6,
                wire7,
                wire8,
                wire9,
                wire10,
                ammeter2,
                wire11,
                rheostat2,
                wire12,
                wire13,
                wire14,
                motor,
                wire15,
                wire16,
                wire17,
                voltmeter1,
                battery2,
                wire18,
                wire19,
                switch1,
                wire20,
                wire21,
                rheostat3,
                wire22,
                ammeter3,
                wire23,
                wire24,
                brake,
                wire25,
                wire26,
                wire27,
                wire28,
                voltmeter2
        );

        final CircuitConnectionServiceImpl connectionService = new CircuitConnectionServiceImpl();

        connectionService.connect(
                battery1, 0,
                wire1, 1
        );
        connectionService.connect(
                wire1, 0,
                wire2, 1
        );
        connectionService.connect(
                wire2, 0,
                ammeter1, 1
        );
        connectionService.connect(
                ammeter1, 0,
                wire3, 1
        );
        connectionService.connect(
                wire3, 0,
                rheostat1, 1
        );
        connectionService.connect(
                rheostat1, 0,
                wire4, 1
        );
        connectionService.connect(
                wire4, 0,
                wire5, 1
        );
        connectionService.connect(
                wire5, 0,
                inductor, 1
        );
        connectionService.connect(
                inductor, 0,
                wire6, 1
        );
        connectionService.connect(
                wire6, 0,
                wire7, 1
        );
        connectionService.connect(
                wire7, 0,
                wire8, 1
        );
        connectionService.connect(
                wire8, 0,
                battery1, 1
        );

        connectionService.connect(
                wire9, 1,
                wire1, 0
        );
        connectionService.connect(
                wire9, 0,
                wire10, 1
        );
        connectionService.connect(
                wire10, 0,
                ammeter2, 1
        );
        connectionService.connect(
                ammeter2, 0,
                wire11, 1
        );
        connectionService.connect(
                wire11, 0,
                rheostat2, 1
        );
        connectionService.connect(
                rheostat2, 0,
                wire12, 1
        );
        connectionService.connect(
                wire12, 0,
                wire13, 1
        );
        connectionService.connect(
                wire13, 0,
                wire14, 1
        );
        connectionService.connect(
                wire14, 0,
                motor, 1
        );
        connectionService.connect(
                motor, 0,
                wire15, 1
        );
        connectionService.connect(
                wire15, 0,
                wire16, 1
        );
        connectionService.connect(
                wire16, 0,
                wire17, 1
        );
        connectionService.connect(
                wire17, 0,
                wire6, 0
        );

        connectionService.connect(
                voltmeter1, 0,
                wire12, 0
        );
        connectionService.connect(
                voltmeter1, 1,
                wire16, 0
        );

        connectionService.connect(
                battery2, 0,
                wire18, 1
        );
        connectionService.connect(
                wire18, 0,
                wire19, 1
        );
        connectionService.connect(
                wire19, 0,
                switch1, 1
        );
        connectionService.connect(
                switch1, 0,
                wire20, 1
        );
        connectionService.connect(
                wire20, 0,
                wire21, 1
        );
        connectionService.connect(
                wire21, 0,
                rheostat3, 1
        );
        connectionService.connect(
                rheostat3, 0,
                wire22, 1
        );
        connectionService.connect(
                wire22, 0,
                ammeter3, 1
        );
        connectionService.connect(
                ammeter3, 0,
                wire23, 1
        );
        connectionService.connect(
                wire23, 0,
                wire24, 1
        );
        connectionService.connect(
                wire24, 0,
                brake, 1
        );
        connectionService.connect(
                brake, 0,
                wire25, 1
        );
        connectionService.connect(
                wire25, 0,
                wire26, 1
        );
        connectionService.connect(
                wire26, 0,
                wire27, 1
        );
        connectionService.connect(
                wire27, 0,
                wire28, 1
        );
        connectionService.connect(
                wire28, 0,
                battery2, 1
        );

        connectionService.connect(
                wire22, 0,
                voltmeter2, 0
        );
        connectionService.connect(
                voltmeter2, 1,
                wire26, 0
        );

        final CircuitService circuitService = new CircuitServiceImpl(circuit);

        builder
                .setType(CircuitService.class)
                .setFactory(() -> circuitService)
                .setScope(InjectionService.Scope.SINGLETON);

    }

}