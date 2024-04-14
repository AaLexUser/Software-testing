package ru.ifmo;

public abstract class AbstractFunction implements Function {
    protected static final double EPS_LIMITS =  1e-5;
    protected static final double[] VALUE_LIMITS = {-1e10, 1e10};
    public double defaultEps = 1e-5;
    public AbstractFunction(){};
    public abstract double apply(double x, double eps);
}
