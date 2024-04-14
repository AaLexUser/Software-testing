package trigonometry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.ifmo.Function;
import ru.ifmo.trigonometry.Sine;
import ru.ifmo.trigonometry.Tangent;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TangentTest {
    private final static double eps = 1e-5;

    private Function tan;

    static Tangent tanMock;

    static Reader tanIn;

    @BeforeAll
    static void init() {
        tanMock = Mockito.mock(Tangent.class);
        try {
            tanIn = new FileReader("src/test/resources/tangent.csv");
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(tanIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(tanMock.apply(Double.parseDouble(record.get(0)), eps))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: \n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
        }

        /* Throw Mockito exception if Infinity */
        Mockito.doThrow(new IllegalArgumentException()).when(tanMock).apply(Double.POSITIVE_INFINITY, eps);
        Mockito.doThrow(new IllegalArgumentException()).when(tanMock).apply(Double.NEGATIVE_INFINITY, eps);
        /* Throw Mockito exception if value more than 1e10 */
        Mockito.doThrow(new IllegalArgumentException()).when(tanMock).apply(1e10, eps);

    }
    @BeforeEach
    void setUp() {
        tan = new Tangent();
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/tangent.csv",
            numLinesToSkip = 1)
    void applyWithSpecialValuesReturnsCorrectResult(double x) {
        assertEquals(tanMock.apply(x, eps), tan.apply(x, eps), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void applyWithInfiniteValueThrowsIllegalArgumentException(double value){
        assertThrows(IllegalArgumentException.class, () -> tan.apply(value, eps));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.MAX_VALUE, -Double.MAX_VALUE})
    void appleWithBigValueAndThrowsIllegalArgumentException(double value) {
        assertThrows(IllegalArgumentException.class, () -> tan.apply(value, eps), "Sine of " + value + " should throw IllegalArgumentException");
    }
}
