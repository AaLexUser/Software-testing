package tests;

import drivers.SeleniumDriver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.UcozCreatesitePage;
import pages.UcozLoginPage;
import pages.UcozPage;
import pages.UcozPanelPage;
import utils.Credentials;

import java.net.URISyntaxException;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.PageUrl.CREATE_SITE;
import static utils.PageUrl.PANEL;

public class UcozPanelTest {
    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void testUcozPanel(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        UcozLoginPage loginPage = ucozPage.clickLoginButton();
        Credentials credentials = new Credentials();
        UcozCreatesitePage createsitePage = loginPage.authentificate(credentials.getEmail(), credentials.getPassword());
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), driver.getWebDriver().getCurrentUrl(), "URL after login does not match expected.");
        UcozPanelPage ucozPanelPage = createsitePage.clickMySiteButton();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        assertEquals(2, driver.getWebDriver().getWindowHandles().size(),
                "Expected number of windows after clicking 'My Site' is not correct.");
        String originalWindow = driver.getWebDriver().getWindowHandle();
        for (String windowHandle : driver.getWebDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.getWebDriver().switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.urlContains(PANEL.getUrl()));
        assertTrue(driver.getWebDriver().getCurrentUrl().contains(PANEL.getUrl()),
                "The URL of the switched window does not contain the expected PANEL URL.");
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        assertEquals(originalWindow, driver.getWebDriver().getWindowHandle(),
                "The original window is not focused after closing the other window.");
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void testUcozUserSearch(SeleniumDriver driver) throws InterruptedException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        UcozLoginPage loginPage = ucozPage.clickLoginButton();
        Credentials credentials = new Credentials();
        UcozCreatesitePage createsitePage = loginPage.authentificate(credentials.getEmail(), credentials.getPassword());
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), driver.getWebDriver().getCurrentUrl(), "URL after login does not match expected.");
        UcozPanelPage ucozPanelPage = createsitePage.clickMySiteButton();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        assertEquals(2, driver.getWebDriver().getWindowHandles().size(),
                "Expected number of windows after clicking 'My Site' is not correct.");
        String originalWindow = driver.getWebDriver().getWindowHandle();
        for (String windowHandle : driver.getWebDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.getWebDriver().switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.urlContains(PANEL.getUrl()));
        assertTrue(driver.getWebDriver().getCurrentUrl().contains(PANEL.getUrl()),
                "The URL of the switched window does not contain the expected PANEL URL.");
        ucozPanelPage.closePopup();
        Thread.sleep(1000);
        ucozPanelPage.clickUsersMenu();
        ucozPanelPage.searchUser("alex");
        Thread.sleep(1000);
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        assertEquals(originalWindow, driver.getWebDriver().getWindowHandle(),
                "The original window is not focused after closing the other window.");
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void testUcozAddUser(SeleniumDriver driver) throws InterruptedException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        UcozLoginPage loginPage = ucozPage.clickLoginButton();
        Credentials credentials = new Credentials();
        UcozCreatesitePage createsitePage = loginPage.authentificate(credentials.getEmail(), credentials.getPassword());
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), driver.getWebDriver().getCurrentUrl(), "URL after login does not match expected.");
        UcozPanelPage ucozPanelPage = createsitePage.clickMySiteButton();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        assertEquals(2, driver.getWebDriver().getWindowHandles().size(),
                "Expected number of windows after clicking 'My Site' is not correct.");
        String originalWindow = driver.getWebDriver().getWindowHandle();
        for (String windowHandle : driver.getWebDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.getWebDriver().switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.urlContains(PANEL.getUrl()));
        assertTrue(driver.getWebDriver().getCurrentUrl().contains(PANEL.getUrl()),
                "The URL of the switched window does not contain the expected PANEL URL.");
        ucozPanelPage.closePopup();
        Thread.sleep(1000);
        ucozPanelPage.clickUsersMenu();
        ucozPanelPage.clickAddUserButton();
        ucozPanelPage.randomFillUserForm();
        ucozPanelPage.clickSaveUserButton();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.urlContains(PANEL.getUrl() + "?a=users"));
        Thread.sleep(1000);
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        assertEquals(originalWindow, driver.getWebDriver().getWindowHandle(),
                "The original window is not focused after closing the other window.");
        driver.quit();
    }
}
