package ru.ifmo.trigonometry;

import ru.ifmo.AbstractFunction;
import ru.ifmo.Function;

public class Tangent extends AbstractFunction {
    private final Sine sine;
    private final Cosine cosine;
    public Tangent() {
        sine = new Sine();
        cosine = new Cosine();
    }
    public Tangent(Sine sine, Cosine cosine) {
        this.sine = sine;
        this.cosine = cosine;
    }
    @Override
    public double apply(double x, double eps) {
        double sin = this.sine.apply(x, eps);
        double cosin = this.cosine.apply(x, eps);
        if (cosin == 0 || Double.isNaN(cosin)) {
            return Double.NaN;
        }
        return sin / cosin;
    }
}
