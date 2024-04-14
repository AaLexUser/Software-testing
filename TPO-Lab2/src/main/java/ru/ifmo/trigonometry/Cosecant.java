package ru.ifmo.trigonometry;

import ru.ifmo.AbstractFunction;
import ru.ifmo.Function;

import static java.lang.Double.isNaN;
import static java.lang.Math.abs;

public class Cosecant extends AbstractFunction {
    private final Sine sine;
    public Cosecant() {
        sine = new Sine();
    }
    public Cosecant(Sine sine) {
        this.sine = sine;
    }
    @Override
    public double apply(double x, double eps) {
        double sin = this.sine.apply(x, eps);
        if (abs(sin) < abs(eps)  || isNaN(sin)) {
            return Double.NaN;
        }
        return 1 / sin;
    }
}
