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
import ru.ifmo.logarithms.NaturalLogarithm;
import ru.ifmo.trigonometry.Cosecant;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NaturalLogarithmTest {
    private final static double eps = 1e-5;

    private Function ln;

    static NaturalLogarithm lnMock;

    static Reader lnIn;

    @BeforeAll
    static void init() {
        lnMock = Mockito.mock(NaturalLogarithm.class);
        try {
            lnIn = new FileReader("src/test/resources/natlog.csv");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(lnIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(lnMock.apply(Double.parseDouble(record.get(0)), eps))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: \n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
        }

    }
    @BeforeEach
    void setUp() {
        ln = new NaturalLogarithm();
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/natlog.csv",
            numLinesToSkip = 1)
    void applyWithSpecialValuesReturnsCorrectResult(double x) {
        assertEquals(lnMock.apply(x, eps), ln.apply(x, eps), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, 1e-6, 1e6})
    void applyIncorrectEpsThrowsException(double eps) {
        assertThrows(IllegalArgumentException.class, () -> ln.apply(1, eps));
    }
}
