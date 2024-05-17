package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PageUrl;
import java.util.Set;

import java.time.Duration;

public abstract class Page {
    protected WebDriver driver;
    final Logger logger = LoggerFactory.getLogger(getClass());

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
        saveClick(element, new WebDriverWait(driver, Duration.ofSeconds(10)));
    }

    protected void saveClick(WebElement element, WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
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

    protected void saveOpenExpectUrl(WebElement button, PageUrl expectedUrl) {
        try {
            String originalUrl = driver.getCurrentUrl();
            saveClick(button);
            int MAX_RETRY = 3;
            for (int attempt = 0; attempt < MAX_RETRY; attempt++) {
                saveClick(button);
                try {
                    new WebDriverWait(driver, Duration.ofSeconds(10))
                            .until(ExpectedConditions.urlMatches(expectedUrl.getUrl() + ".*"));
                    return;
                } catch (Exception e) {
                    handleRetry(originalUrl);
                }
            }
        } catch (Exception e) {
            logger.error("Error opening new page:  {}", e.getMessage());
        }
    }
    protected void saveOpenExpectUrlInNewTab(WebElement button, PageUrl expectedUrl) {
        final int MAX_RETRY = 3;
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();

        try {
            for (int attempt = 0; attempt < MAX_RETRY; attempt++) {
                WebDriverWait waiit = new WebDriverWait(driver, Duration.ofSeconds(10));
                saveClick(button, waiit);
                waiit.until(d ->
                        d.getWindowHandles().size() > existingWindows.size());
                for (String windowHandle : driver.getWindowHandles()) {
                    if (!existingWindows.contains(windowHandle)) {
                        driver.switchTo().window(windowHandle);
                        break;
                    }
                }

                boolean urlMatched = waiit.until(ExpectedConditions.urlMatches(expectedUrl.getUrl() + ".*"));

                if (urlMatched) {
                    return;
                } else {
                    driver.close();
                    driver.switchTo().window(originalWindow);
                }
            }
        } catch (Exception e) {
            logger.error("Error opening new page: {}", e.getMessage());
            driver.switchTo().window(originalWindow);
        }
    }


    private void handleRetry(String originalUrl) throws InterruptedException {
        String currentUrlAfterRefresh = driver.getCurrentUrl();
        if (!currentUrlAfterRefresh.equals(originalUrl)) {
            throw new RuntimeException("URL has changed, expected: " + originalUrl + ", but found: " + currentUrlAfterRefresh);
        }
        logger.info("Still on the same page. I'll try again, URL: {}", currentUrlAfterRefresh);
        long SLEEP_IN_MILLIS = 1000;
        Thread.sleep(SLEEP_IN_MILLIS);
        refresh();
    }


}
