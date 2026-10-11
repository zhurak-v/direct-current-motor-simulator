# Direct Current Motor Simulator

A Java application for simulating a DC motor inside an electrical circuit. The simulator combines circuit analysis with motor dynamics: the user builds a circuit, applies a load, and watches how current, back-EMF, torque and rotational speed evolve over time.

## Highlights

- **Time-domain simulation.** The system is advanced step by step, so start-up, acceleration and steady-state behavior can be observed rather than only the final values.
- **Electromechanical coupling.** Electrical and mechanical parts influence each other: armature current produces torque, torque changes speed, and speed produces back-EMF that in turn changes the current.
- **Realistic element models.** Batteries have internal resistance, the inductor has both active resistance and inductance, and measuring devices have their own resistance.
- **Adjustable load and braking.** Load torque can be changed at runtime, and an asynchronous brake adds a speed- and current-dependent braking torque.
- **Numerical circuit solving.** The circuit is built as a graph of elements connected through typed pins, and its equations are solved numerically at every time step.

## Supported Elements

| Element | Description |
|---|---|
| Battery | voltage source with adjustable voltage and internal resistance |
| Wire | connection with optional resistance |
| Switch | opens or closes a branch |
| Rheostat | adjustable resistor |
| Inductor | active resistance plus inductance, with transient behavior |
| DC Motor | armature resistance, back-EMF, motor torque, load torque and inertia |
| Asynchronous Brake | braking element with stator resistance and a brake coefficient |
| Ammeter | measures current through a branch |
| Voltmeter | measures potential difference between two points |

## Motor Model

The motor is described by three parameters: armature resistance, the machine constant `Ce·Φ`, and the moment of inertia `J`.

```
E   = Ce·Φ · n                 back-EMF
M   = 9550 · (E · I / 1000) / n motor torque at running speed
M   = 9.55 · Ce·Φ · I           starting torque
a   = (M - Mload) / J            angular acceleration
n   = n + a · Δt                 speed update per time step
```

At each step the motor receives the actual armature current from the circuit, updates its torque and speed, and exposes back-EMF, speed and torque for display. Speed is clamped at zero, so the motor does not spin backwards.

### Dynamic Behavior

- **Start-up.** At standstill there is no back-EMF, so the current is limited only by the resistances in the circuit, and the motor develops its starting torque.
- **Acceleration.** As the speed grows, back-EMF rises and the current decreases.
- **Steady state.** Speed settles where motor torque equals load torque.
- **Load changes.** Increasing the load torque slows the motor down and increases the current drawn from the source.

### Inductor Transients

The inductor is handled in discrete time: its inductance is converted into an equivalent resistance `L / Δt`, added to the active resistance. This lets current build up gradually after switching, as in a real circuit.

### Asynchronous Brake

The brake produces a torque proportional to the square of the current and to the speed:

```
Mbrake = k · I² · n
```

## Working with the Model

Elements are plain entities with a simple API:

```java
final BatteryElementEntity battery = new BatteryElementEntity(1L, 24.0d, 0.1d);
final RheostatElementEntity rheostat = new RheostatElementEntity(2L, 5.0d);
final InductorElementEntity inductor = new InductorElementEntity(3L, 0.2d, 0.05d);
final EngineElementEntity engine = new EngineElementEntity(4L, 0.5d, 0.2d, 0.05d);
final SwitchElementEntity switchElement = new SwitchElementEntity(5L);

final CircuitEntity circuit = new CircuitEntity(1L);
circuit.addElements(battery, rheostat, inductor, engine, switchElement);

switchElement.close();
battery.setVoltage(48.0d);
rheostat.setResistance(2.0d);
engine.setLoadTorque(1.5d);
```

Advancing the simulation by one time step:

```java
engine.step(0.01d, armatureCurrent);
inductor.step(0.01d, armatureCurrent);

final double speed = engine.getSpeed();
final double backEmf = engine.getE();
final double torque = engine.getMotorTorque();
```

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | core language and simulation logic |
| Numerical linear algebra | solving the circuit equations at each time step |

## Use Cases

- Studying start-up and braking of a DC motor
- Exploring the effect of a rheostat on motor speed and starting current
- Demonstrating the relationship between load torque, current and speed
- Educational labs and coursework on electrical machines