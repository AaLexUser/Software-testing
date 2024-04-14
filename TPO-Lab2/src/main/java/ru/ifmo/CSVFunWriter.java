package ru.ifmo;

import java.io.IOException;
import java.io.OutputStream;

public class CSVFunWriter {
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private final Function function;
    private final OutputStream os;

    public CSVFunWriter(Function function, OutputStream os) {
        this.function = function;
        this.os = os;
    }

    public void write(double from, double to, double step) throws IOException {
        os.write(("x" + COMMA_DELIMITER + "y" + NEW_LINE_SEPARATOR).getBytes());
        for (double x = from; x <= to; x += step) {
            os.write((x + COMMA_DELIMITER + function.apply(x, 1e-5) + NEW_LINE_SEPARATOR).getBytes());
        }
    }
}
