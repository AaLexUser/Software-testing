package math;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static java.lang.Math.*;

import static org.junit.jupiter.api.Assertions.*;


class SineTest {

    private static double DELTA;
    Sine sine;

    @BeforeAll
    static void setUp() {
        DELTA = 1e-5;
    }

    @BeforeEach
    void setUpEach() {
        sine = new Sine();
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI / 2, Math.PI, 3 * Math.PI / 2, 2 * Math.PI})
    void applyWithSpecialValuesReturnsCorrectResult(double value) {
        assertEquals(sin(value), sine.apply(value, DELTA), DELTA, "Sine of " + value + " should be " + sin(value));
    }
    @ParameterizedTest
    @ValueSource(doubles = {0.001, -0.001, (PI / 2) + 0.001, (PI / 2) - 0.001, PI + 0.001, PI - 0.001, (3 * PI / 2) + 0.001, (3 * PI / 2) - 0.001, (2 * PI) + 0.001, (2 * PI) - 0.001})
    void applyNearSpecialValuesReturnsCorrectResult(double value) {
        assertEquals(sin(value), sine.apply(value, DELTA), DELTA, "Sine of " + value + " should be " + sin(value));
    }
    @ParameterizedTest
    @ValueSource(doubles = {Double.MIN_VALUE, -Double.MIN_VALUE, -1e10, 1e10})
    void applyEgdeCasesReturnsCorrectResult(double value) {
        assertEquals(sin(value), sine.apply(value, DELTA), DELTA, "Sine of " + value + " should be " + sin(value));
    }
    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void applyWithInfiniteValueThrowsIllegalArgumentException(double value) {
        assertThrows(IllegalArgumentException.class, () -> sine.apply(value, DELTA), "Sine of " + value + " should throw IllegalArgumentException");
    }
    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN})
    void applyWithNaNThrowsIllegalArgumentException(double value) {
        assertThrows(IllegalArgumentException.class, () -> sine.apply(value, DELTA), "Sine of " + value + " should throw IllegalArgumentException");
    }
    @ParameterizedTest
    @ValueSource(doubles = {Double.MAX_VALUE, -Double.MAX_VALUE})
    void appleWithBigValueAndThrowsIllegalArgumentException(double value) {
        assertThrows(IllegalArgumentException.class, () -> sine.apply(value, DELTA), "Sine of " + value + " should throw IllegalArgumentException");
    }

    @ParameterizedTest
    @ValueSource(doubles = {1e-10, -1e-10, 1e10, 1})
    void appleWithBigEpsAndThrowsIllegalArgumentException(double eps) {
        double value = PI/2;
        assertThrows(IllegalArgumentException.class, () -> sine.apply(value, eps), "Sine of " + value + " should throw IllegalArgumentException");
    }

    @ParameterizedTest
    @ValueSource(doubles = {1e10+1, -1e10-1})
    void applyWithOutOfRangeValueThrowsIllegalArgumentException(double value) {
        assertThrows(IllegalArgumentException.class, () -> sine.apply(value), "Sine of " + value + " should throw IllegalArgumentException");
    }
}