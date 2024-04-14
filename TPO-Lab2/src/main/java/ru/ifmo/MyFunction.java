package ru.ifmo;

import ru.ifmo.logarithms.Logarithms;
import ru.ifmo.logarithms.NaturalLogarithm;
import ru.ifmo.trigonometry.*;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static ru.ifmo.logarithms.LogBase.*;

public class MyFunction extends AbstractFunction{
    private final Function sine;
    private final Function sec;
    private final Function csc;
    private final Function tan;
    private final Function log3;
    private final Function log5;
    private final Function ln;

    public MyFunction() {
        sine = new Sine();
        sec = new Secant();
        csc = new Cosecant();
        tan = new Tangent();
        log3 = new Logarithms(LOG_BASE_3);
        log5 = new Logarithms(LOG_BASE_5);
        ln = new NaturalLogarithm();
    }

    public MyFunction(Function sine, Function sec, Function csc, Function tan, Function log3, Function log5, Function ln) {
        this.sine = sine;
        this.sec = sec;
        this.csc = csc;
        this.tan = tan;
        this.log3 = log3;
        this.log5 = log5;
        this.ln = ln;
    }


    public double apply(double x, double eps){
        if( x <= 0) {
            if(abs(x % Math.PI) < eps) {
                return Double.NaN;
            } else if (abs(x % Math.PI + Math.PI/2) < eps)  {
                return Double.NaN;
            }
            return (pow(sec.apply(x, eps), 3) / csc.apply(x, eps)) / (sine.apply(x, eps) + tan.apply(x, eps));
        }
        else {
            if(x == 1.0) {
                return Double.NaN;
            }
            return ( ( (pow(log3.apply(x, eps), 3) / ln.apply(x, eps)) / (ln.apply(x, eps) * log3.apply(x, eps)) - log5.apply(x, eps) ) - log5.apply(x, eps) );
        }
    }
}
