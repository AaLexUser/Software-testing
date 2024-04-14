package ru.ifmo.trigonometry;

import ru.ifmo.AbstractFunction;
import ru.ifmo.Function;

import static java.lang.Math.abs;

public class Cosine extends AbstractFunction {
    private final Sine sine;
    public Cosine() {
        sine = new Sine();
    }
    public Cosine(Sine sine) {
        this.sine = sine;
    }
    @Override
    public double apply(double x, double eps) {
       double cosRes = sine.apply(x + Math.PI / 2, (eps));
        if(abs(cosRes) < abs(eps)){
            return 0;
        }
        return cosRes;
    }
}
