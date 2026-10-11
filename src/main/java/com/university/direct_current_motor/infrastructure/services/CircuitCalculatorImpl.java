package com.university.direct_current_motor.infrastructure.services;

import com.university.direct_current_motor.core.entities.*;
import com.university.direct_current_motor.core.values.Pin;
import org.apache.commons.math3.linear.*;

import java.util.*;
import java.util.stream.Collectors;

public final class CircuitCalculatorImpl {

    private final CircuitEntity circuit;
    private final List<Set<Pin>> physicalNodes;
    private final Map<Set<Pin>, Integer> nodeToIndexMap;
    private final List<BatteryElementEntity> voltageSources;
    private double deltaTime = 0.01;
    private RealVector lastSolutionVector = null;

    public CircuitCalculatorImpl(final CircuitEntity circuit) {
        this.circuit = Objects.requireNonNull(circuit);
        this.physicalNodes = this.findPhysicalNodes();
        this.nodeToIndexMap = this.buildNodeIndexMap();
        this.voltageSources = this.identifyVoltageSources();
    }

    public void setDeltaTime(final double deltaTime) {
        this.deltaTime = deltaTime;
    }

    public void calculate() {
        if (this.physicalNodes.isEmpty()) {
            return;
        }

        this.updateDynamicElementsState(this.lastSolutionVector);

        final int numberOfNodes = this.physicalNodes.size();
        final int numberOfSources = this.voltageSources.size();
        final int matrixSize = numberOfNodes + numberOfSources;

        final RealMatrix conductanceMatrix = new Array2DRowRealMatrix(matrixSize, matrixSize);
        final RealVector constantVector = new ArrayRealVector(matrixSize);

        this.applyPassiveElementsStamps(conductanceMatrix);
        this.applyVoltageSourcesStamps(conductanceMatrix, constantVector, numberOfNodes);
        this.applyGroundReference(conductanceMatrix, numberOfNodes);
        this.applyGminStepping(conductanceMatrix, numberOfNodes);

        final SingularValueDecomposition svd = new SingularValueDecomposition(conductanceMatrix);
        final DecompositionSolver decompositionSolver = svd.getSolver();

        try {
            final RealVector solutionVector = decompositionSolver.solve(constantVector);
            this.lastSolutionVector = solutionVector;
            this.applyResultsToMeasurementDevices(solutionVector);
        } catch (Exception ex) {
            this.clearAllDevices();
        }
    }

    private void updateDynamicElementsState(final RealVector solutionVector) {
        AsynchronousBrakeEntity brake = null;

        for (final AbstractCircuitElementEntity element : this.circuit.getElements()) {
            if (element instanceof AsynchronousBrakeEntity asyncBrake) {
                brake = asyncBrake;
                double currentBrake = this.findCurrentByElement(brake, solutionVector);
                brake.setCurrent(currentBrake);
            } else if (element instanceof InductorElementEntity inductor) {
                double currentField = this.findCurrentByElement(inductor, solutionVector);
                inductor.step(this.deltaTime, currentField);
            }
        }

        for (final AbstractCircuitElementEntity element : this.circuit.getElements()) {
            if (element instanceof EngineElementEntity engine) {
                double voltageDrop = (solutionVector != null)
                        ? this.calculateVoltageDifference(engine, solutionVector)
                        : 0.0;

                double realRotorCurrent = 0.0;

                if (solutionVector != null && engine.getArmatureResistance() > 0) {
                    double E = engine.getE();

                    if (voltageDrop > E) {
                        realRotorCurrent = (voltageDrop - E) / engine.getArmatureResistance();
                    } else {
                        realRotorCurrent = 0.0;
                    }
                }

                if (brake != null) {
                    double dynamicLoadTorque = brake.calculateTorque(engine.getSpeed());
                    engine.setLoadTorque(dynamicLoadTorque + 0.1);
                }

                engine.step(this.deltaTime, realRotorCurrent);
            }
        }
    }

    private double findCurrentByElement(final AbstractCircuitElementEntity element, final RealVector solutionVector) {
        if (solutionVector == null) {
            return 0.0;
        }

        double voltageDrop = this.calculateVoltageDifference(element, solutionVector);
        double resistance = this.getEffectiveResistance(element);

        if (resistance >= Resistable.MAX_RESISTANCE) {
            return 0.0;
        }

        return voltageDrop / resistance;
    }

    private void applyGminStepping(final RealMatrix matrix, final int numberOfNodes) {
        final double gmin = 1e-12;
        for (int i = 0; i < numberOfNodes; i++) {
            matrix.addToEntry(i, i, gmin);
        }
    }

    private void clearAllDevices() {
        for (final AbstractCircuitElementEntity element : this.circuit.getElements()) {
            if (element instanceof AmmeterElementEntity ammeter) {
                ammeter.setCurrent(0.0d);
            } else if (element instanceof VoltmeterElementEntity voltmeter) {
                voltmeter.setMeasuredVoltage(0.0d);
            }
        }
    }

    private Map<Set<Pin>, Integer> buildNodeIndexMap() {
        final Map<Set<Pin>, Integer> map = new HashMap<>();
        for (int index = 0; index < this.physicalNodes.size(); index++) {
            map.put(this.physicalNodes.get(index), index);
        }
        return map;
    }

    private List<BatteryElementEntity> identifyVoltageSources() {
        return this.circuit.getElements().stream()
                .filter(element -> element instanceof BatteryElementEntity)
                .map(element -> (BatteryElementEntity) element)
                .collect(Collectors.toList());
    }

    private void applyPassiveElementsStamps(final RealMatrix conductanceMatrix) {
        for (final AbstractCircuitElementEntity element : this.circuit.getElements()) {
            if (!(element instanceof BatteryElementEntity)) {
                final double resistance = this.getEffectiveResistance(element);
                if (resistance < Resistable.MAX_RESISTANCE) {
                    this.stampResistor(conductanceMatrix, element, resistance);
                }
            }
        }
    }

    private void applyVoltageSourcesStamps(final RealMatrix matrix, final RealVector vector, final int nodeOffset) {
        for (int sourceIndex = 0; sourceIndex < this.voltageSources.size(); sourceIndex++) {
            final BatteryElementEntity battery = this.voltageSources.get(sourceIndex);
            this.stampVoltageSource(matrix, vector, battery, sourceIndex, nodeOffset);
        }
    }

    private void applyGroundReference(final RealMatrix matrix, final int numberOfNodes) {
        if (numberOfNodes > 0) {
            matrix.addToEntry(0, 0, Resistable.MIN_RESISTANCE);
        }
    }

    private double getEffectiveResistance(final AbstractCircuitElementEntity element) {
        if (element instanceof SwitchElementEntity switchElement) {
            return switchElement.isOpen() ? Resistable.MAX_RESISTANCE : Resistable.MIN_RESISTANCE;
        }

        if (element instanceof Resistable resistable) {
            final double resistanceValue = resistable.getResistance();
            return (resistanceValue <= 0.0d) ? Resistable.MIN_RESISTANCE : resistanceValue;
        }

        return Resistable.MIN_RESISTANCE;
    }

    private void stampResistor(final RealMatrix matrix, final AbstractCircuitElementEntity element, final double resistance) {
        final int firstNodeIndex = this.getNodeIndex(element.getPins().get(0));
        final int secondNodeIndex = this.getNodeIndex(element.getPins().get(1));

        if (firstNodeIndex == secondNodeIndex) {
            return;
        }

        final double conductance = 1.0 / resistance;

        matrix.addToEntry(firstNodeIndex, firstNodeIndex, conductance);
        matrix.addToEntry(secondNodeIndex, secondNodeIndex, conductance);
        matrix.addToEntry(firstNodeIndex, secondNodeIndex, -conductance);
        matrix.addToEntry(secondNodeIndex, firstNodeIndex, -conductance);
    }

    private void stampVoltageSource(final RealMatrix matrix, final RealVector vector, final BatteryElementEntity battery, final int sourceIndex, final int nodeOffset) {
        final int positiveNodeIndex = this.getNodeIndex(this.findPinByType(battery, Pin.PinType.POSITIVE));
        final int negativeNodeIndex = this.getNodeIndex(this.findPinByType(battery, Pin.PinType.NEGATIVE));
        final int currentVariableIndex = nodeOffset + sourceIndex;

        if (positiveNodeIndex != negativeNodeIndex) {
            matrix.addToEntry(positiveNodeIndex, currentVariableIndex, 1.0);
            matrix.addToEntry(negativeNodeIndex, currentVariableIndex, -1.0);
            matrix.addToEntry(currentVariableIndex, positiveNodeIndex, 1.0);
            matrix.addToEntry(currentVariableIndex, negativeNodeIndex, -1.0);
        }

        vector.setEntry(currentVariableIndex, battery.getVoltage());
    }

    private List<Set<Pin>> findPhysicalNodes() {
        final Set<Pin> visitedPins = new HashSet<>();
        final List<Set<Pin>> detectedNodes = new ArrayList<>();

        for (final AbstractCircuitElementEntity element : this.circuit.getElements()) {
            for (final Pin pin : element.getPins()) {
                if (!visitedPins.contains(pin)) {
                    final Set<Pin> newNode = new HashSet<>();
                    this.traversePinConnections(pin, newNode);
                    visitedPins.addAll(newNode);
                    detectedNodes.add(newNode);
                }
            }
        }
        return detectedNodes;
    }

    private void traversePinConnections(final Pin currentPin, final Set<Pin> nodeGroup) {
        nodeGroup.add(currentPin);
        for (final Pin neighborPin : currentPin.getConnections()) {
            if (!nodeGroup.contains(neighborPin)) {
                this.traversePinConnections(neighborPin, nodeGroup);
            }
        }
    }

    private void applyResultsToMeasurementDevices(final RealVector solutionVector) {
        for (final AbstractCircuitElementEntity element : this.circuit.getElements()) {
            if (element instanceof AmmeterElementEntity ammeter) {
                final double currentMeasured = this.calculateCurrent(ammeter, solutionVector);
                ammeter.setCurrent(currentMeasured);
            } else if (element instanceof VoltmeterElementEntity voltmeter) {
                final double voltageMeasured = this.calculateVoltageDifference(voltmeter, solutionVector);
                voltmeter.setMeasuredVoltage(voltageMeasured);
            }
        }
    }

    private double calculateVoltageDifference(final AbstractCircuitElementEntity element, final RealVector solutionVector) {
        final double firstPinPotential = solutionVector.getEntry(this.getNodeIndex(element.getPins().get(0)));
        final double secondPinPotential = solutionVector.getEntry(this.getNodeIndex(element.getPins().get(1)));
        return Math.abs(firstPinPotential - secondPinPotential);
    }

    private double calculateCurrent(final AbstractCircuitElementEntity element, final RealVector solutionVector) {
        Objects.requireNonNull(element);
        Objects.requireNonNull(solutionVector);

        final double voltageDifference = this.calculateVoltageDifference(element, solutionVector);
        final double effectiveResistance = this.getEffectiveResistance(element);

        if (effectiveResistance >= Resistable.MAX_RESISTANCE) {
            return 0.0d;
        }

        return voltageDifference / effectiveResistance;
    }

    private int getNodeIndex(final Pin pin) {
        Objects.requireNonNull(pin);
        return this.nodeToIndexMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().contains(pin))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(0);
    }

    private Pin findPinByType(final AbstractCircuitElementEntity element, final Pin.PinType type) {
        Objects.requireNonNull(element);
        Objects.requireNonNull(type);

        return element.getPins().stream()
                .filter(pin -> pin.getType() == type)
                .findFirst()
                .orElse(element.getPins().get(0));
    }
}