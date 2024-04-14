package ru.ifmo.trigonometry;

import ru.ifmo.AbstractFunction;
import ru.ifmo.Function;

import static java.lang.Math.abs;

public class Secant extends AbstractFunction {
    private final Cosine cosine;
    public Secant() {
        cosine = new Cosine();
    }
    public Secant(Cosine cosine) {
        this.cosine = cosine;
    }
    @Override
    public double apply(double x, double eps) {
        double cos = cosine.apply(x, eps);
        if (abs(cos) < abs(eps) || Double.isNaN(cos)) {
            return Double.NaN;
        }
        return 1 / cos;
    }
}
