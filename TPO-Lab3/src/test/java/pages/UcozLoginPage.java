package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.PageUrl;

// page_url = https://www.ucoz.ru/login
public class UcozLoginPage extends Page {
    @FindBy(xpath = "//*[@id='fEmail']")
    public WebElement inputEmail;

    @FindBy(xpath = "//*[@id='fPassword']")
    public WebElement inputPassword;

    @FindBy(xpath = "//*[@id='submit_btn']")
    public WebElement buttonSubmitLogin;
    

    public UcozLoginPage(WebDriver driver) {
        super(driver);
        open(PageUrl.LOGIN);
        PageFactory.initElements(driver, this);
    }

    public UcozCreatesitePage authentificate(String email, String password) {
        inputEmail.sendKeys(email);
        inputPassword.sendKeys(password);
        buttonSubmitLogin.click();
        return new UcozCreatesitePage(driver);
    }
    

}