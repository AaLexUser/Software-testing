package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PageUrl;

// page_url = https://www.ucoz.ru/
public class UcozPage extends Page {
    @FindBy(xpath = "//a[contains(@href, 'signup')]")
    public WebElement signUpButton;

    @FindBy(xpath = "//a[contains(@href, 'login')]")
    public WebElement loginButton;

    @FindBy(xpath = "//button[@class='menu-btn-v5']")
    public WebElement menuButton;

    @FindBy(xpath = "//a[@href='https://www.ucoz.ru/tour']")
    public WebElement tourButton;

    @FindBy(xpath = "/html/body/section/nav/a[3]")
    public WebElement blogButton;

    @FindBy(xpath = "/html/body/section/nav/a[4]")
    public WebElement forumButton;

    @FindBy(xpath = "//a[@href='https://www.ucoz.ru/pricing/']")
    public WebElement pricingButton;

    @FindBy(xpath = "//a[@href='https://ru.uteam.pro/']")
    public WebElement jobsButton;

    @FindBy(xpath = "/html/body/section/nav/a[7]")
    public WebElement examplesButton;

    @FindBy(xpath = "/html/body/section/nav/a[8]")
    public WebElement helpButton;

    @FindBy(xpath = "/html/body/section/nav/a[9]")
    public WebElement contactButton;
    

    public UcozPage(WebDriver driver) {
        super(driver);
        open(PageUrl.UCOZ);
        PageFactory.initElements(driver, this);
    }

    public void clickSignUpButton() {
        saveClick(signUpButton);
    }

    public UcozLoginPage clickLoginButton() {
        saveClick(loginButton);
        return new UcozLoginPage(driver);
    }

    public void clickMenuButton() {
        saveClick(menuButton);
    }

    public void clickTourButton() {
        saveClick(tourButton);

    }

    public void clickBlogButton() {
        saveClick(blogButton);
    }

    public void clickForumButton() {
        saveClick(forumButton);
    }

    public void clickPricingButton() {
        saveClick(pricingButton);
    }

    public void clickJobsButton() {
        saveClick(jobsButton);
    }

    public void clickExamplesButton() {
        saveClick(examplesButton);
    }

    public void clickHelpButton() {
        saveClick(helpButton);
    }

    public void clickContactButton() {
        saveClick(contactButton);
    }
}