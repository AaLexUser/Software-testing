import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;
import ru.ifmo.CSVFunWriter;
import ru.ifmo.Function;
import ru.ifmo.MyFunction;
import ru.ifmo.logarithms.LogBase;
import ru.ifmo.logarithms.Logarithms;
import ru.ifmo.logarithms.NaturalLogarithm;
import ru.ifmo.trigonometry.*;
import static org.hamcrest.Matchers.closeTo;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static ru.ifmo.logarithms.LogBase.LOG_BASE_3;
import static ru.ifmo.logarithms.LogBase.LOG_BASE_5;

public class MyFunctionTest {
    private static double eps = 1e-3;
    private Function myFunction;

    static Cosecant cscMock;
    static Cosine cosMock;
    static Secant secMock;
    static Sine sinMock;
    static Tangent tanMock;
    static NaturalLogarithm lnMock;
    static Logarithms log3Mock;
    static Logarithms log5Mock;
    static MyFunction myFunctionMock;

    static Reader cscIn;
    static Reader cosIn;
    static Reader secIn;
    static Reader sinIn;
    static Reader tanIn;
    static Reader lnIn;
    static Reader log3In;
    static Reader log5In;
    static Reader myFunctionIn;

    @BeforeAll
    static void init() {
        cscMock = Mockito.mock(Cosecant.class);
        cosMock = Mockito.mock(Cosine.class);
        secMock = Mockito.mock(Secant.class);
        sinMock = Mockito.mock(Sine.class);
        tanMock = Mockito.mock(Tangent.class);
        lnMock = Mockito.mock(NaturalLogarithm.class);
        log3Mock = Mockito.mock(Logarithms.class);
        log5Mock = Mockito.mock(Logarithms.class);
        myFunctionMock = Mockito.mock(MyFunction.class);

        try {
            cscIn = new FileReader("src/test/resources/integration/cosecant.csv");
            cosIn = new FileReader("src/test/resources/integration/cosine.csv");
            secIn = new FileReader("src/test/resources/integration/secant.csv");
            sinIn = new FileReader("src/test/resources/integration/sine.csv");
            tanIn = new FileReader("src/test/resources/integration/tangent.csv");
            lnIn = new FileReader("src/test/resources/integration/natlog.csv");
            log3In = new FileReader("src/test/resources/integration/log3.csv");
            log5In = new FileReader("src/test/resources/integration/log5.csv");
            myFunctionIn = new FileReader("src/test/resources/integration/myFunc.csv");

            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(cscIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(cscMock.apply(eq(Double.parseDouble(record.get(0))), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(cosIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(cosMock.apply(eq(Double.parseDouble(record.get(0))), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(secIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(secMock.apply(eq(Double.parseDouble(record.get(0))), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(sinIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(sinMock.apply(doubleThat(value -> Math.abs(value - Double.parseDouble(record.get(0))) <= eps), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(tanIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(tanMock.apply(eq(Double.parseDouble(record.get(0))), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(lnIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(lnMock.apply(doubleThat(value -> Math.abs(value - Double.parseDouble(record.get(0))) <= eps), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(log3In);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(log3Mock.apply(eq(Double.parseDouble(record.get(0))), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(log5In);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(log5Mock.apply(eq(Double.parseDouble(record.get(0))), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }

            records = CSVFormat.DEFAULT.parse(myFunctionIn);
            records.iterator().next();
            for (CSVRecord record : records) {
                Mockito
                        .when(myFunctionMock.apply(eq(Double.parseDouble(record.get(0))), anyDouble()))
                        .thenReturn(Double.parseDouble(record.get(1)));
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: \n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
        }
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void applyWithSpecialValuesReturnsCorrectResult(double x) {
        myFunction = new MyFunction();
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), eps);
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocks(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, tanMock, log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptSine(double x) {
        myFunction = new MyFunction(new Sine(), secMock, cscMock, tanMock, log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptSecWithMocks(double x) {
        myFunction = new MyFunction(sinMock, new Secant(cosMock), cscMock, tanMock, log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptSecWithCosineWithMocks(double x) {
        myFunction = new MyFunction(sinMock, new Secant(new Cosine(sinMock)), cscMock, tanMock, log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptSecWithCosineWithSine(double x) {
        myFunction = new MyFunction(sinMock, new Secant(new Cosine(new Sine())), cscMock, tanMock, log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptCosecant(double x) {
        myFunction = new MyFunction(sinMock, secMock, new Cosecant(sinMock), tanMock, log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptCosecantWithSine(double x) {
        myFunction = new MyFunction(sinMock, secMock, new Cosecant(new Sine()), tanMock, log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptTangent(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, new Tangent(sinMock, cosMock), log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptTangentWithSine(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, new Tangent(new Sine(), cosMock), log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptTangentWithCosine(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, new Tangent(sinMock, new Cosine(sinMock)), log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptTangentWithCosineWithSine(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, new Tangent(new Sine(), new Cosine(new Sine())), log3Mock, log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptLog3(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, tanMock, new Logarithms(LOG_BASE_3, lnMock), log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptLog3withLn(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, tanMock, new Logarithms(LOG_BASE_3, new NaturalLogarithm()), log5Mock, lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptLog5(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, tanMock, log3Mock, new Logarithms(LOG_BASE_5, lnMock), lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptLog5WithLn(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, tanMock, log3Mock, new Logarithms(LOG_BASE_5, new NaturalLogarithm()), lnMock);
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWithMocksExceptLn(double x) {
        myFunction = new MyFunction(sinMock, secMock, cscMock, tanMock, log3Mock, log5Mock, new NaturalLogarithm());
        assertEquals(myFunctionMock.apply(x, eps), myFunction.apply(x, eps), 1);
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testFunctionWriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(myFunction, new FileOutputStream("src/test/resources/csvOut/myFunc.csv"));
            writer.write(-2*Math.PI, 2*Math.PI, Math.PI/100);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            assertFalse(true);
        }
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testSineWriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new Sine(), new FileOutputStream("src/test/resources/csvOut/Sine.csv"));
            writer.write(-2*Math.PI, 2*Math.PI, Math.PI/100);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            assertFalse(true);
        }
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testCosineWriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new Cosine(), new FileOutputStream("src/test/resources/csvOut/Cosine.csv"));
            writer.write(-2*Math.PI, 2*Math.PI, Math.PI/100);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            assertFalse(true);
        }
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testSecantWriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new Secant(), new FileOutputStream("src/test/resources/csvOut/Secant.csv"));
            writer.write(-2*Math.PI, 2*Math.PI, Math.PI/100);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            assertFalse(true);
        }
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testCosecantWriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new Cosecant(), new FileOutputStream("src/test/resources/csvOut/Cosecant.csv"));
            writer.write(-2*Math.PI, 2*Math.PI, Math.PI/100);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            assertFalse(true);
        }
    }
    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testTangentWriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new Tangent(), new FileOutputStream("src/test/resources/csvOut/Tangent.csv"));
            writer.write(-2*Math.PI, 2*Math.PI, Math.PI/100);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            fail();
        }
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testNaturalLogarithmWriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new NaturalLogarithm(), new FileOutputStream("src/test/resources/csvOut/NaturalLogarithm.csv"));
            writer.write(0.1, 10, 0.1);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            fail();
        }
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testLogarithmBase3WriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new Logarithms(LOG_BASE_3), new FileOutputStream("src/test/resources/csvOut/LogarithmBase3.csv"));
            writer.write(0.1, 10, 0.1);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            fail();
        }
    }

    @ParameterizedTest(name = "x = {0}")
    @CsvFileSource(
            resources = "/integration/integrationIn.csv",
            numLinesToSkip = 1)
    void testLogarithmBase5WriteToCSV(double x) {
        myFunction = new MyFunction();
        try{
            CSVFunWriter writer = new CSVFunWriter(new Logarithms(LOG_BASE_5), new FileOutputStream("src/test/resources/csvOut/LogarithmBase5.csv"));
            writer.write(0.1, 10, 0.1);
        }
        catch (IOException e) {
            System.err.println("IOException: \n" + e.getMessage());
            fail();
        }
    }


}
