package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PageUrl;

import java.time.Duration;

public abstract class Page {
    protected WebDriver driver;
    public Page(WebDriver driver) {
        this.driver = driver;
    }

    protected final void open(PageUrl url) {
        driver.get(url.getUrl());
    }

    public final Duration getTimeout() {
        return driver.manage().timeouts().getImplicitWaitTimeout();
    }

    public final void setTimeout(Duration duration) {
        driver.manage().timeouts().implicitlyWait(duration);
    }

    public final void refresh() {
        driver.navigate().refresh();
    }
    public final String getTitle() {
        return driver.getTitle();
    }
    public final String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected void saveClick(WebElement element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(element));
            element.click();
        } catch (Exception e) {
            // Log the exception or handle it as per your error handling policy
            System.out.println("Error clicking on element: " + e.getMessage());
        }
    }
    protected void safeSetInputField(WebElement element, String value) {
        try {
            element.clear();
            element.sendKeys(value);
        } catch (Exception e) {
            // Log the exception or handle it as per your error handling policy
            System.out.println("Error setting input field: " + e.getMessage());
        }
    }

    protected void safeSelectDropdown(WebElement element, String value) {
        try {
            Select monthSelect = new Select(element);
            monthSelect.selectByVisibleText(value);
        } catch (Exception e) {
            // Log the exception or handle it as per your error handling policy
            System.out.println("Error selecting dropdown: " + e.getMessage());
        }
    }


}
