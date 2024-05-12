package drivers;

import org.openqa.selenium.WebDriver;

public abstract class SeleniumDriver {
    protected WebDriver driver;

    public final WebDriver getWebDriver() {
        return driver;
    }

    public abstract void create();
    public abstract void quit();
}
