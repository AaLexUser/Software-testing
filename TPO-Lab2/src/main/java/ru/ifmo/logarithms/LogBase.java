package ru.ifmo.logarithms;

public enum LogBase {
    LOG_BASE_3(3),
    LOG_BASE_5(5);

    private double value;

    LogBase(double base) {
        this.value = base;
    }

    public double value() {
        return value;
    }

}
