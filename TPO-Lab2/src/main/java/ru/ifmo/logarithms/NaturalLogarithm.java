package ru.ifmo.logarithms;

import ru.ifmo.AbstractFunction;
import ru.ifmo.Function;

/**
 * This class represents a natural logarithm function approximation using a Taylor series.
 */
public class NaturalLogarithm extends AbstractFunction {

    public double apply(double value, double eps) {
        if (value < 0 || Double.isNaN(value)) {
            return Double.NaN;
        }
        if (value == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (Double.isInfinite(value)) {
            return Double.POSITIVE_INFINITY;
        }
        if(Double.isNaN(eps)){
            throw new IllegalArgumentException("The precision is NaN");
        }
        if (eps < EPS_LIMITS || eps >= 1) {
            throw new IllegalArgumentException("The precision must be in the range [-1e-5, 1e-5]");
        }

        double term = -1;
        double result = 0;
        /*
         * Logarithm function approximation using a Taylor series.
         * ln( ( 1 + y ) / ( 1 - y ) ) = 2 * ( y + y^3 / 3 + y^5 / 5 + ... )
         * x = ( 1 + y ) / ( 1 - y ) => y = ( x - 1 ) / ( x + 1 )
         * ln(x) = 2 * ( y + y^3 / 3 + y^5 / 5 + ... )
         */
        double y = (value - 1) / (value + 1);
        for (long i = 1; Math.abs(term) >= (eps*1e-2) ; i+=2) {
            term = 2 * Math.pow(y, i) / i;
            result += term;
        }
        return result;
    }
}
