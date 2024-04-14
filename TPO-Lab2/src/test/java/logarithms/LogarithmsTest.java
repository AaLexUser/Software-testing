package logarithms;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.ifmo.Function;
import ru.ifmo.logarithms.LogBase;
import ru.ifmo.logarithms.Logarithms;
import ru.ifmo.logarithms.NaturalLogarithm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.ifmo.logarithms.LogBase.LOG_BASE_3;
import static ru.ifmo.logarithms.LogBase.LOG_BASE_5;

public class LogarithmsTest {
    private final static double eps = 1e-5;

    private Function log3;
    private Function log5;

    @BeforeEach
    void setUp() {
        log3 = new Logarithms(LOG_BASE_3);
        log5 = new Logarithms(LOG_BASE_5);
    }

    @ParameterizedTest(name = "x = {0}, f(x) = {1}")
    @CsvFileSource(
            resources = "/log3.csv",
            numLinesToSkip = 1)
    void applyLog3GetCorrectResult(double x, double expected) {
        assertEquals(expected, log3.apply(x, eps), eps);
    }

    @ParameterizedTest(name = "x = {0}, f(x) = {1}")
    @CsvFileSource(
            resources = "/log5.csv",
            numLinesToSkip = 1)
    void applyLog5GetCorrectResult(double x, double expected) {
        assertEquals(expected, log5.apply(x, eps), eps);
    }
}
