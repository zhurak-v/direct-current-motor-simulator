package com.university.direct_current_motor.adapters.desktop.feautures.components;

import com.university.direct_current_motor.adapters.desktop.annotations.Route;
import com.university.direct_current_motor.adapters.desktop.common.components.*;
import com.university.direct_current_motor.core.entities.EngineElementEntity;
import com.university.direct_current_motor.core.ports.services.CircuitService;
import com.university.direct_current_motor.core.ports.services.InjectionService;
import com.university.direct_current_motor.core.values.Resolution;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Route(name = "home-screen")
public final class HomeScreen extends Screen {

    public static final List<ExperimentData> experimentDataList = new ArrayList<>();

    public static final class ExperimentData {
        private final SimpleIntegerProperty id;
        private final SimpleDoubleProperty voltmeter1;
        private final SimpleDoubleProperty voltmeter2;
        private final SimpleDoubleProperty ammeter1;
        private final SimpleDoubleProperty ammeter2;
        private final SimpleDoubleProperty ammeter3;
        private final SimpleDoubleProperty rheostat1;
        private final SimpleDoubleProperty rheostat2;
        private final SimpleDoubleProperty rheostat3;
        private final SimpleDoubleProperty speed;
        private final SimpleDoubleProperty torque;

        public ExperimentData(final int id, final double voltmeter1, final double voltmeter2,
                              final double ammeter1, final double ammeter2, final double ammeter3,
                              final double rheostat1, final double rheostat2, final double rheostat3,
                              final double speed, final double torque) {
            this.id = new SimpleIntegerProperty(id);
            this.voltmeter1 = new SimpleDoubleProperty(voltmeter1);
            this.voltmeter2 = new SimpleDoubleProperty(voltmeter2);
            this.ammeter1 = new SimpleDoubleProperty(ammeter1);
            this.ammeter2 = new SimpleDoubleProperty(ammeter2);
            this.ammeter3 = new SimpleDoubleProperty(ammeter3);
            this.rheostat1 = new SimpleDoubleProperty(rheostat1);
            this.rheostat2 = new SimpleDoubleProperty(rheostat2);
            this.rheostat3 = new SimpleDoubleProperty(rheostat3);
            this.speed = new SimpleDoubleProperty(speed);
            this.torque = new SimpleDoubleProperty(torque);
        }

        public SimpleIntegerProperty idProperty() { return this.id; }
        public SimpleDoubleProperty voltmeter1Property() { return this.voltmeter1; }
        public SimpleDoubleProperty voltmeter2Property() { return this.voltmeter2; }
        public SimpleDoubleProperty ammeter1Property() { return this.ammeter1; }
        public SimpleDoubleProperty ammeter2Property() { return this.ammeter2; }
        public SimpleDoubleProperty ammeter3Property() { return this.ammeter3; }
        public SimpleDoubleProperty rheostat1Property() { return this.rheostat1; }
        public SimpleDoubleProperty rheostat2Property() { return this.rheostat2; }
        public SimpleDoubleProperty rheostat3Property() { return this.rheostat3; }
        public SimpleDoubleProperty speedProperty() { return this.speed; }
        public SimpleDoubleProperty torqueProperty() { return this.torque; }
    }

    public static final class MainSection extends Box {

        private static final class CircuitKey extends Box {
            private final Media openMedia;
            private final Media closeMedia;
            private boolean isOpen;
            private Consumer<Boolean> onStateChanged;

            public CircuitKey(final String openPath, final String closePath, final double w, final double h) {
                super();
                this.isOpen = true;
                this.openMedia = new Media(openPath);
                this.closeMedia = new Media(closePath);

                this.openMedia.setStaticSize(w, h);
                this.closeMedia.setStaticSize(w, h);

                this.setDirection(Direction.Y_DIRECTION);
                this.setAlign(Align.CENTER, Align.CENTER);
                this.setWidth(w);
                this.setHeight(h);
                this.addInlineStyles("-fx-cursor: hand;");

                this.onClick((event, component) -> this.toggle());
                this.updateMedia();
            }

            public void setOnStateChanged(final Consumer<Boolean> onStateChanged) {
                this.onStateChanged = onStateChanged;
            }

            public void toggle() {
                this.setState(!this.isOpen);
            }

            public void setState(final boolean open) {
                if (this.isOpen != open) {
                    this.isOpen = open;
                    this.updateMedia();
                    if (this.onStateChanged != null) {
                        this.onStateChanged.accept(open);
                    }
                }
            }

            private void updateMedia() {
                this.removeChildren(this.openMedia, this.closeMedia);
                this.addChildren(this.isOpen ? this.openMedia : this.closeMedia);
            }
        }

        private static final class Typer extends Box {
            private final Input input;
            private final Typography error;
            private final Typography unit;
            private Consumer<Double> onValid;

            public Typer(final double initialValue, final String unitName) {
                super();
                this.input = new PrimaryInput();
                this.error = new PrimaryTopography("");
                this.unit = new PrimaryTopography(Objects.requireNonNull(unitName));

                this.setWidth(90);
                this.setDirection(Direction.Y_DIRECTION);
                this.setAlign(Align.CENTER, Align.CENTER);
                this.error.addInlineStyles("-fx-font-size: 10px; -fx-text-fill: red;");
                this.unit.addInlineStyles("-fx-font-size: 11px;");

                this.input.setText(String.valueOf(initialValue));
                this.input.onTarget(this::handleInput);

                final Box wrapper = new Box(Direction.X_DIRECTION);
                wrapper.setAlign(Align.SPACE_BETWEEN, Align.CENTER);
                wrapper.addChildren(this.input, this.unit);
                this.addChildren(wrapper, this.error);
            }

            public void onValid(final Consumer<Double> onValid) {
                this.onValid = onValid;
            }

            public double getValue() {
                try {
                    return Double.parseDouble(this.input.getText());
                } catch (final NumberFormatException e) {
                    return 0.0;
                }
            }

            private void handleInput(final String currentValue, final Input inputComponent) {
                try {
                    final double parsedValue = Double.parseDouble(currentValue);
                    if (parsedValue < 0) {
                        throw new IllegalArgumentException("Мінус?");
                    }
                    if (this.onValid != null) {
                        this.onValid.accept(parsedValue);
                    }
                    this.clearError();
                } catch (final NumberFormatException ex) {
                    this.showError("Помилка");
                } catch (final Exception ex) {
                    this.showError(ex.getMessage());
                }
            }

            private void clearError() {
                this.error.setText("");
                this.input.removeInlineStyles("-fx-border-color: red;");
            }

            private void showError(final String msg) {
                this.input.addInlineStyles("-fx-border-color: red;");
                this.error.setText(msg);
            }
        }

        private static final class Indicator extends Box {
            private final Typography valueLabel;
            private final String unit;

            public Indicator(final double initialValue, final String unit) {
                super();
                this.unit = Objects.requireNonNull(unit);
                this.valueLabel = new PrimaryTopography("");
                this.valueLabel.addInlineStyles("-fx-font-size: 12px; -fx-font-weight: bold;");
                this.updateText(initialValue);
                this.addChildren(this.valueLabel);
            }

            public void updateValue(final double value) {
                if (Platform.isFxApplicationThread()) {
                    this.updateText(value);
                } else {
                    Platform.runLater(() -> this.updateText(value));
                }
            }

            private void updateText(final double value) {
                final double rounded = Math.round(value * 10.0) / 10.0;
                this.valueLabel.setText(rounded + " " + this.unit);
            }
        }

        private final Canvas canvas;
        private final TableView<ExperimentData> tableView;
        private final ObservableList<ExperimentData> tableRows;
        private final CircuitService circuitService;

        private final CircuitKey circuitKeySwitch1;
        private final Typer rRheostat1Typer;
        private final Typer rRheostat2Typer;
        private final Typer rRheostat3Typer;

        private final Indicator voltmeter1Indicator;
        private final Indicator voltmeter2Indicator;
        private final Indicator ammeter1Indicator;
        private final Indicator ammeter2Indicator;
        private final Indicator ammeter3Indicator;

        private final Indicator speedIndicator;
        private final Indicator torqueIndicator;

        private boolean isShownOnce;
        private double cachedHeaderHeight;
        private boolean isTableMode;
        private javafx.animation.Timeline simulationTimeline;

        public MainSection() {
            super();
            this.canvas = new Canvas(272, 520);
            this.tableView = new TableView<>();
            this.tableRows = FXCollections.observableArrayList();
            this.isShownOnce = false;
            this.cachedHeaderHeight = 0.0;
            this.isTableMode = false;

            this.circuitService = InjectionService.getInjectionService().getBean(CircuitService.class);

            this.circuitKeySwitch1 = new CircuitKey("/images/SA1-open.png", "/images/SA1-close.png", 8, 11);

            this.rRheostat1Typer = new Typer(this.circuitService.findRheostatById(6L).getResistance(), "Ом Rp");
            this.rRheostat2Typer = new Typer(this.circuitService.findRheostatById(17L).getResistance(), "Ом");
            this.rRheostat3Typer = new Typer(this.circuitService.findRheostatById(32L).getResistance(), "Ом");

            this.voltmeter1Indicator = new Indicator(this.circuitService.findVoltmeterById(25L).getVoltage(), "В");
            this.voltmeter2Indicator = new Indicator(this.circuitService.findVoltmeterById(42L).getVoltage(), "В");
            this.ammeter1Indicator = new Indicator(this.circuitService.findAmmeterById(4L).getCurrent(), "А");
            this.ammeter2Indicator = new Indicator(this.circuitService.findAmmeterById(15L).getCurrent(), "А");
            this.ammeter3Indicator = new Indicator(this.circuitService.findAmmeterById(34L).getCurrent(), "А");

            final EngineElementEntity motor = this.circuitService.findEngineById(21L);
            this.speedIndicator = new Indicator(motor.getSpeed(), "об/хв");
            this.torqueIndicator = new Indicator(motor.getMotorTorque(), "Н·м");

            this.initBindings();
            this.initTable();
            this.applyStyles();
            this.render();

            this.startSimulationTimeline();
        }

        private void startSimulationTimeline() {
            this.simulationTimeline = new javafx.animation.Timeline(
                    new javafx.animation.KeyFrame(
                            javafx.util.Duration.millis(1),
                            event -> {
                                this.circuitService.nextTick();
                                this.refreshIndicators();
                            }
                    )
            );
            this.simulationTimeline.setCycleCount(javafx.animation.Timeline.INDEFINITE);
            this.simulationTimeline.play();
        }

        public void stopSimulation() {
            if (this.simulationTimeline != null) {
                this.simulationTimeline.stop();
            }
        }

        private void initBindings() {
            this.circuitKeySwitch1.setOnStateChanged(isOpen -> {
                if (isOpen) {
                    this.circuitService.openSwitch(29L);
                } else {
                    this.circuitService.closeSwitch(29L);
                }
                this.refreshIndicators();
            });

            this.rRheostat1Typer.onValid(value -> {
                this.circuitService.changeRheostatResistance(6L, value);
                this.refreshIndicators();
            });

            this.rRheostat2Typer.onValid(value -> {
                this.circuitService.changeRheostatResistance(17L, value);
                this.refreshIndicators();
            });

            this.rRheostat3Typer.onValid(value -> {
                this.circuitService.changeRheostatResistance(32L, value);
                this.refreshIndicators();
            });

            this.onClick((event, component) -> {
                if (!(event.getTarget() instanceof TextField)) {
                    this.getNode().requestFocus();
                }
            });
        }

        private void initTable() {
            this.tableView.setPrefSize(780, 520);
            this.tableView.setMaxSize(1200, 520);

            final TableColumn<ExperimentData, Integer> colId = new TableColumn<>("№");
            colId.setCellValueFactory(d -> d.getValue().idProperty().asObject());
            colId.setPrefWidth(35);

            final TableColumn<ExperimentData, Double> colV1 = new TableColumn<>("В V1");
            colV1.setCellValueFactory(d -> d.getValue().voltmeter1Property().asObject());
            colV1.setPrefWidth(65);

            final TableColumn<ExperimentData, Double> colV2 = new TableColumn<>("В V2");
            colV2.setCellValueFactory(d -> d.getValue().voltmeter2Property().asObject());
            colV2.setPrefWidth(65);

            final TableColumn<ExperimentData, Double> colA1 = new TableColumn<>("А A2");
            colA1.setCellValueFactory(d -> d.getValue().ammeter1Property().asObject());
            colA1.setPrefWidth(65);

            final TableColumn<ExperimentData, Double> colA2 = new TableColumn<>("А A1");
            colA2.setCellValueFactory(d -> d.getValue().ammeter2Property().asObject());
            colA2.setPrefWidth(65);

            final TableColumn<ExperimentData, Double> colA3 = new TableColumn<>("А A3");
            colA3.setCellValueFactory(d -> d.getValue().ammeter3Property().asObject());
            colA3.setPrefWidth(65);

            final TableColumn<ExperimentData, Double> colRh1 = new TableColumn<>("Ом Rp");
            colRh1.setCellValueFactory(d -> d.getValue().rheostat1Property().asObject());
            colRh1.setPrefWidth(70);

            final TableColumn<ExperimentData, Double> colRh2 = new TableColumn<>("Ом Rп");
            colRh2.setCellValueFactory(d -> d.getValue().rheostat2Property().asObject());
            colRh2.setPrefWidth(70);

            final TableColumn<ExperimentData, Double> colRh3 = new TableColumn<>("Ом Rнав");
            colRh3.setCellValueFactory(d -> d.getValue().rheostat3Property().asObject());
            colRh3.setPrefWidth(75);

            final TableColumn<ExperimentData, Double> colSpeed = new TableColumn<>("об/хв n");
            colSpeed.setCellValueFactory(d -> d.getValue().speedProperty().asObject());
            colSpeed.setPrefWidth(85);

            final TableColumn<ExperimentData, Double> colTorque = new TableColumn<>("Н·м M");
            colTorque.setCellValueFactory(d -> d.getValue().torqueProperty().asObject());
            colTorque.setPrefWidth(75);

            this.tableView.getColumns().addAll(colId, colV1, colV2, colA1, colA2, colA3, colRh1, colRh2, colRh3, colSpeed, colTorque);
            this.tableView.setItems(this.tableRows);
        }

        public void saveCurrentStateToTable() {
            final double rawV1 = this.circuitService.findVoltmeterById(25L).getVoltage();
            final double rawV2 = this.circuitService.findVoltmeterById(42L).getVoltage();
            final double rawA1 = this.circuitService.findAmmeterById(4L).getCurrent();
            final double rawA2 = this.circuitService.findAmmeterById(15L).getCurrent();
            final double rawA3 = this.circuitService.findAmmeterById(34L).getCurrent();

            final EngineElementEntity motor = this.circuitService.findEngineById(21L);
            final double rawSpeed = motor.getSpeed();
            final double rawTorque = motor.getMotorTorque();

            final double v1 = Math.round(rawV1 * 10.0) / 10.0;
            final double v2 = Math.round(rawV2 * 10.0) / 10.0;
            final double a1 = Math.round(rawA1 * 10.0) / 10.0;
            final double a2 = Math.round(rawA2 * 10.0) / 10.0;
            final double a3 = Math.round(rawA3 * 10.0) / 10.0;
            final double speed = Math.round(rawSpeed * 10.0) / 10.0;
            final double torque = Math.round(rawTorque * 10.0) / 10.0;

            final double rh1 = this.rRheostat1Typer.getValue();
            final double rh2 = this.rRheostat2Typer.getValue();
            final double rh3 = this.rRheostat3Typer.getValue();

            final int nextId = this.tableRows.size() + 1;
            final ExperimentData data = new ExperimentData(nextId, v1, v2, a1, a2, a3, rh1, rh2, rh3, speed, torque);

            this.tableRows.add(data);
            HomeScreen.experimentDataList.add(data);
        }

        public boolean toggleViewMode() {
            this.isTableMode = !this.isTableMode;
            this.tableView.setVisible(this.isTableMode);
            this.tableView.setManaged(this.isTableMode);
            this.canvas.getNode().setVisible(!this.isTableMode);
            this.canvas.getNode().setManaged(!this.isTableMode);

            if (!this.isTableMode) {
                Platform.runLater(() -> this.getNode().requestFocus());
            }
            return this.isTableMode;
        }

        public void refreshIndicators() {
            if (this.circuitService != null) {
                this.voltmeter1Indicator.updateValue(this.circuitService.findVoltmeterById(25L).getVoltage());
                this.voltmeter2Indicator.updateValue(this.circuitService.findVoltmeterById(42L).getVoltage());
                this.ammeter1Indicator.updateValue(this.circuitService.findAmmeterById(4L).getCurrent());
                this.ammeter2Indicator.updateValue(this.circuitService.findAmmeterById(15L).getCurrent());
                this.ammeter3Indicator.updateValue(this.circuitService.findAmmeterById(34L).getCurrent());

                final EngineElementEntity motor = this.circuitService.findEngineById(21L);
                this.speedIndicator.updateValue(motor.getSpeed());
                this.torqueIndicator.updateValue(motor.getMotorTorque());
            }
        }

        private void render() {
            this.addChildren(this.canvas);
            this.getNode().getChildren().add(this.tableView);

            this.tableView.setVisible(false);
            this.tableView.setManaged(false);

            this.canvas.addChildren(42, 403, this.circuitKeySwitch1);

            this.canvas.addChildren(60.0, 150.0, this.rRheostat1Typer);
            this.canvas.addChildren(-50.0, 112.0, this.rRheostat2Typer);
            this.canvas.addChildren(-30.0, 360.0, this.rRheostat3Typer);

            this.canvas.addChildren(140.0, 200.0, this.voltmeter1Indicator);
            this.canvas.addChildren(140.0, 355.0, this.voltmeter2Indicator);

            this.canvas.addChildren(115.0, 89.0, this.ammeter1Indicator);
            this.canvas.addChildren(-5, 87, this.ammeter2Indicator);
            this.canvas.addChildren(-5, 305, this.ammeter3Indicator);

            this.canvas.addChildren(55.0, 245.0, this.speedIndicator);
            this.canvas.addChildren(55.0, 265.0, this.torqueIndicator);
        }

        private void applyStyles() {
            this.setDirection(Direction.Y_DIRECTION);
            this.setAlign(Align.CENTER, Align.CENTER);

            final String imageUrl = Objects.requireNonNull(Canvas.class.getResource("/images/circuit-diagram.jpg")).toExternalForm();
            this.canvas.addInlineStyles("""
                    -fx-background-image: url('%s');
                    -fx-background-size: cover;
                    -fx-background-position: center center;
                """.formatted(imageUrl));

            this.setupScaling();
        }

        private void setupScaling() {
            final Resolution res = InjectionService.getInjectionService().getBean(Resolution.class);

            this.getNode().sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.windowProperty().addListener((obsW, oldWin, newWin) -> {
                        if (newWin != null) {
                            final javafx.stage.Window win = newWin;

                            win.setOnShown(event -> {
                                if (!this.isShownOnce) {
                                    final double headerH = win.getHeight() - newScene.getHeight();
                                    if (!Double.isNaN(headerH)) {
                                        this.cachedHeaderHeight = headerH;
                                    }
                                    this.isShownOnce = true;
                                }
                            });

                            final Runnable scaler = () -> {
                                final double sw = win.getWidth() / res.getWidth();
                                final double sh = win.getHeight() / (res.getHeight() + this.cachedHeaderHeight);
                                final double aspect = Math.min(sw, sh);
                                this.canvas.getNode().setScaleX(aspect);
                                this.canvas.getNode().setScaleY(aspect);

                                this.tableView.setScaleX(aspect);
                                this.tableView.setScaleY(aspect);
                            };

                            win.widthProperty().addListener((o, ov, nv) -> scaler.run());
                            win.heightProperty().addListener((o, ov, nv) -> scaler.run());
                        }
                    });
                }
            });
        }
    }

    public static final class FooterSection extends Box {
        private final List<Button> buttons;

        public FooterSection(final HomeScreen homeScreen) {
            super();
            this.setDirection(Direction.X_DIRECTION);
            this.addInlineStyles("-fx-background-color: #eaeef6;");
            this.setAlign(Align.CENTER, Align.CENTER);

            final Button saveBtn = new PrimaryButton();
            saveBtn.addChildren(new PrimaryTopography("Зберегти дослідні дані"));
            saveBtn.setMargin(0.0, 12.0);
            saveBtn.setPadding(8.0, 12.0);
            saveBtn.onClick((e, c) -> homeScreen.mainSection.saveCurrentStateToTable());

            final Typography viewTitle = new PrimaryTopography("Переглянути результат");
            final Button viewBtn = new PrimaryButton();
            viewBtn.addChildren(viewTitle);
            viewBtn.setMargin(0.0, 12.0);
            viewBtn.setPadding(8.0, 12.0);
            viewBtn.onClick((e, c) -> {
                final boolean isTable = homeScreen.mainSection.toggleViewMode();
                if (isTable) {
                    viewTitle.setText("Повернутись до схеми");
                } else {
                    viewTitle.setText("Переглянути результат");
                }
            });

            this.buttons = List.of(saveBtn, viewBtn);
            this.addChildren(this.buttons.toArray(new Button[0]));
        }
    }

    private final Header header;
    private final MainSection mainSection;
    private final FooterSection footerSection;

    public HomeScreen() {
        super();
        this.header = new Header();
        this.mainSection = new HomeScreen.MainSection();
        this.footerSection = new FooterSection(this);

        this.applyLayout();
        this.addChildren(this.header, this.mainSection, this.footerSection);
    }

    private void applyLayout() {
        this.header.bindWidthPercent(this, 100.0);
        this.header.setHeight(80.0);

        this.mainSection.bindWidthPercent(this, 100.0);
        this.mainSection.bindHeightPercent(this, 100.0, 160);

        this.footerSection.bindWidthPercent(this, 100.0);
        this.footerSection.setHeight(80.0);
    }
}