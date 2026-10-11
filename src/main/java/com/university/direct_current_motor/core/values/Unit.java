package com.university.direct_current_motor.core.values;

import java.util.Objects;

public enum Unit {

    AMPERE("А"),
    OHM("Ом"),
    VOLT("В"),
    WATT("Вт"),
    JOULE("Дж"),
    SECOND("с"),
    METER("м"),
    METER_PER_SECOND("м/с"),
    METER_PER_SECOND_SQUARED("м/с²"),
    KILOGRAM("кг"),
    NEWTON("Н"),
    HERTZ("Гц"),
    DEGREE("°"),
    RADIAN("рад"),
    REVOLUTION_PER_MINUTE("об/хв"),
    NEWTON_METER("Н·м");

    private final String symbol;

    Unit(final String symbol) {
        Objects.requireNonNull(symbol);
        this.symbol = symbol;
    }

    public final String getSymbol() {
        return this.symbol;
    }

}
