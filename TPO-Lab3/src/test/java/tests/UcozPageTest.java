package tests;

import drivers.SeleniumDriver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.UcozLoginPage;
import pages.UcozPage;
import utils.Credentials;
import utils.PageUrl;
import utils.PrettifyUrl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.PageUrl.*;

public class UcozPageTest {
    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void clickSignUpButtonNotThrowException(SeleniumDriver driver) throws MalformedURLException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        assertEquals("Бесплатный конструктор сайтов. Создайте свой сайт самостоятельно!", ucozPage.getTitle());
        assertEquals(PageUrl.UCOZ.getUrl(),ucozPage.getCurrentUrl());
        ucozPage.clickSignUpButton();
        URL actualUrl = new URL(ucozPage.getCurrentUrl());
        assertEquals("Создать свой сайт - uCoz", ucozPage.getTitle());
        assertEquals(SIGNUP.getUrl(),actualUrl.getProtocol()+ "://" + actualUrl.getHost() + actualUrl.getPath());
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void clickLoginButtonNotThrowException(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        assertEquals("Бесплатный конструктор сайтов. Создайте свой сайт самостоятельно!", ucozPage.getTitle());
        assertEquals(PageUrl.UCOZ.getUrl(),ucozPage.getCurrentUrl());
        UcozLoginPage loginPage = ucozPage.clickLoginButton();
        assertEquals("uCoz - вход в систему", ucozPage.getTitle());
        assertEquals(LOGIN.getUrl(),PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        Credentials credentials = new Credentials();
        loginPage.authentificate(credentials.getEmail(), credentials.getPassword());
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        assertEquals("Создать свой сайт - uCoz", loginPage.getTitle());
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void clickLoginButtonAuthUserNotThrowException(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        assertEquals("Бесплатный конструктор сайтов. Создайте свой сайт самостоятельно!", ucozPage.getTitle());
        assertEquals(PageUrl.UCOZ.getUrl(),ucozPage.getCurrentUrl());
        UcozLoginPage loginPage = ucozPage.clickLoginButton();
        assertEquals("uCoz - вход в систему", ucozPage.getTitle());
        assertEquals(LOGIN.getUrl(),PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        Credentials credentials = new Credentials();
        loginPage.authentificate(credentials.getEmail(), credentials.getPassword());
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        assertEquals("Создать свой сайт - uCoz", loginPage.getTitle());
        ucozPage = new UcozPage(driver.getWebDriver());
        ucozPage.clickLoginButton();
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        assertEquals("Создать свой сайт - uCoz", loginPage.getTitle());
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void clickRegButtonAuthUserNotThrowException(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        assertEquals("Бесплатный конструктор сайтов. Создайте свой сайт самостоятельно!", ucozPage.getTitle());
        assertEquals(PageUrl.UCOZ.getUrl(),ucozPage.getCurrentUrl());
        UcozLoginPage loginPage = ucozPage.clickLoginButton();
        assertEquals("uCoz - вход в систему", ucozPage.getTitle());
        assertEquals(LOGIN.getUrl(),PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        Credentials credentials = new Credentials();
        loginPage.authentificate(credentials.getEmail(), credentials.getPassword());
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        assertEquals("Создать свой сайт - uCoz", loginPage.getTitle());
        ucozPage = new UcozPage(driver.getWebDriver());
        ucozPage.clickSignUpButton();
        wait.until(ExpectedConditions.urlToBe(CREATE_SITE.getUrl()));
        assertEquals(CREATE_SITE.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        assertEquals("Создать свой сайт - uCoz", loginPage.getTitle());
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewTourNotThrowException(SeleniumDriver driver) throws URISyntaxException  {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        String originalWindow = driver.getWebDriver().getWindowHandle();
        ucozPage.clickMenuButton();
        ucozPage.clickTourButton();
        assertEquals("Тур по системе uCoz", ucozPage.getTitle());
        assertEquals(TOUR.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }



    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewBlogNotThrowException(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        String originalWindow = driver.getWebDriver().getWindowHandle();
        ucozPage.clickMenuButton();
        ucozPage.clickBlogButton();
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(BLOG.getUrl()));
        assertEquals("Официальный блог компании uCoz", ucozPage.getTitle());
        assertEquals(BLOG.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewForumNotThrowException(SeleniumDriver driver) throws URISyntaxException, InterruptedException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        String originalWindow = driver.getWebDriver().getWindowHandle();
        ucozPage.clickMenuButton();
        ucozPage.clickForumButton();
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(300));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWebDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.getWebDriver().switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.urlToBe(FORUM.getUrl()));
        Thread.sleep(1000);
        wait.until(ExpectedConditions.titleIs("Сообщество uCoz"));
        assertEquals("Сообщество uCoz", ucozPage.getTitle());
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewPricesNotThrowException(SeleniumDriver driver) throws URISyntaxException, InterruptedException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        ucozPage.clickMenuButton();
        String originalWindow = driver.getWebDriver().getWindowHandle();
        ucozPage.clickPricingButton();
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.titleIs("Цены на услуги - uCoz"));
        assertEquals("Цены на услуги - uCoz", ucozPage.getTitle());
        assertEquals(PRICING.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }
    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewJobsNotThrowException(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        ucozPage.clickMenuButton();
        String originalWindow = driver.getWebDriver().getWindowHandle();
        ucozPage.clickJobsButton();
        assertEquals("Главная - Работа в uTeam", ucozPage.getTitle());
        assertEquals(JOBS.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewExamplesNotThrowException(SeleniumDriver driver) throws URISyntaxException, InterruptedException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        ucozPage.clickMenuButton();
        ucozPage.clickExamplesButton();
        String originalWindow = driver.getWebDriver().getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        for (String windowHandle : driver.getWebDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.getWebDriver().switchTo().window(windowHandle);
                break;
            }
        }
        Thread.sleep(1000);
        wait.until(ExpectedConditions.titleIs("Истории успеха - Примеры сайтов, которые добились успеха вместе с uCoz"));
        assertEquals("Истории успеха - Примеры сайтов, которые добились успеха вместе с uCoz", ucozPage.getTitle());
        assertEquals(EXAMPLES.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }
    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewHelpNotThrowException(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        ucozPage.clickMenuButton();
        ucozPage.clickHelpButton();
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        String originalWindow = driver.getWebDriver().getWindowHandle();
        for (String windowHandle : driver.getWebDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.getWebDriver().switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.urlToBe(HELP.getUrl()));
        assertEquals("База знаний uCoz", ucozPage.getTitle());
        assertEquals(HELP.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }

    @ParameterizedTest
    @MethodSource("utils.DriverSrc#provideDrivers")
    public void viewContactNotThrowException(SeleniumDriver driver) throws URISyntaxException {
        driver.create();
        UcozPage ucozPage = new UcozPage(driver.getWebDriver());
        ucozPage.clickMenuButton();
        ucozPage.clickContactButton();
        WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        String originalWindow = driver.getWebDriver().getWindowHandle();
        for (String windowHandle : driver.getWebDriver().getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.getWebDriver().switchTo().window(windowHandle);
                break;
            }
        }
        wait.until(ExpectedConditions.urlToBe(CONTACT.getUrl()));
        assertEquals("uCoz - связаться с нами", ucozPage.getTitle());
        assertEquals(CONTACT.getUrl(), PrettifyUrl.prettify(ucozPage.getCurrentUrl()));
        driver.getWebDriver().close();
        driver.getWebDriver().switchTo().window(originalWindow);
        driver.quit();
    }




}
