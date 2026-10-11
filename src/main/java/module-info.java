module com.university.direct_current_motor {
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires jakarta.validation;
    requires jdk.jdi;
    requires jdk.jfr;
    requires java.desktop;
    requires org.slf4j;
    requires commons.math3;

    exports com.university.direct_current_motor.application;
}