package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.PageUrl;

import java.util.List;

// page_url = https://www.ucoz.ru/createsite
public class UcozCreatesitePage extends Page{
    @FindBy(xpath = "//a[contains(@class, 'nowrap')]")
    public WebElement mySiteButton;

    public UcozCreatesitePage(WebDriver driver) {
        super(driver);
        open(PageUrl.CREATESITE);
        PageFactory.initElements(driver, this);
    }

    public UcozPanelPage clickMySiteButton() {
        mySiteButton.click();
        return new UcozPanelPage(driver);
    }
}