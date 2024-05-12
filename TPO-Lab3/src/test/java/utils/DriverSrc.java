package utils;
import drivers.SeleniumChromeDriver;
import drivers.SeleniumFirefoxDriver;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DriverSrc {

    static Stream<Arguments> provideDrivers() {
        return Stream.of(
                Arguments.of(new SeleniumChromeDriver()),
                Arguments.of(new SeleniumFirefoxDriver())
        );
    }
}
