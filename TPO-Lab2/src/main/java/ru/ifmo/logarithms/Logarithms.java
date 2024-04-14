package ru.ifmo.logarithms;

import ru.ifmo.AbstractFunction;
import ru.ifmo.Function;

public class Logarithms extends AbstractFunction {
    private final NaturalLogarithm naturalLogarithm;
    private final LogBase base;
    public Logarithms(LogBase base) {
        this.naturalLogarithm = new NaturalLogarithm();
        this.base = base;
    }
    public Logarithms(LogBase base, NaturalLogarithm naturalLogarithm) {
        this.base = base;
        this.naturalLogarithm = naturalLogarithm;
    }

    @Override
    public double apply(double x, double eps) {
        return naturalLogarithm.apply(x, eps) / naturalLogarithm.apply(base.value(), eps);
    }
}
