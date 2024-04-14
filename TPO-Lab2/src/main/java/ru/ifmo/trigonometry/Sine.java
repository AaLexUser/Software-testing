package ru.ifmo.trigonometry;

import ru.ifmo.AbstractFunction;
import ru.ifmo.Function;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.*;

/**
 * This class represents a sine function approximation using a Taylor series.
 */
public class Sine extends AbstractFunction {
    /**
     * The default precision used for the Taylor series approximation.
     */

    /**
     * Applies the sine function to a given value using a specified precision.
     *
     * @param value The value to apply the sine function to.
     * @param eps The precision to use for the Taylor series approximation.
     * @return The result of the sine function applied to the given value.
     */
    public double apply(double value, double eps) {
        if(Double.isNaN(value)) {
            return Double.NaN;
        }
        if(!Double.isFinite(value)) {
            throw new IllegalArgumentException("The value must be finite");
        }
        if(value < VALUE_LIMITS[0] || value > VALUE_LIMITS[1]) {
            throw new IllegalArgumentException("The value must be in the range [-1e10, 1e10]");
        }
        if(eps < EPS_LIMITS || eps >= 1) {
            throw new IllegalArgumentException("The precision must be in the range [-1e-5, 1e-5]");
        }

        value = value % (2 * PI);  // reduce the value to the range [-2PI, 2PI]

        double result = 0;
        double term = value;
        int sign = 1;
        double prevTerm = 0;
        for (long i = 1; !(Math.abs(term - prevTerm) < (eps*1e-5)); i++) {
            result += sign * term;
            sign *= -1;
            prevTerm = term;
            term *= value * (value / (2 * i * (2 * i + 1)));
        }
//        BigDecimal bd = new BigDecimal(Double.toString(result));
//        bd = bd.setScale((int) -Math.log10(eps), RoundingMode.HALF_UP);
//        return bd.doubleValue();
        return result;
    }
}
