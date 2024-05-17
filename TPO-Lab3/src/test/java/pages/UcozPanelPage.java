package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.FullNameGenerator;
import utils.PageUrl;
import org.openqa.selenium.support.ui.Select;
import utils.SecurePasswordGenerator;
import utils.UsernameGenerator;

import java.util.Random;

// page_url = https://tpo-lab3.ucoz.net/panel/?a=cp
public class UcozPanelPage extends Page {
    @FindBy(xpath = "//div[contains(@class, 'fancybox-close')]")
    public WebElement closePopup;

    @FindBy(xpath = "//div[contains(@class, 'MmenuOut')][.//*[@id='musers']]")
    public WebElement usersMenu;

    @FindBy(xpath = "//input[@name='u']")
    public WebElement searchInput;

    @FindBy(xpath = "//*[@id='subbutfrm942']")
    public WebElement buttonSearch;

    @FindBy(xpath = "//a[@href='/panel/?a=users&l=add']")
    public WebElement addUserButton;

    @FindBy(xpath = "//input[@name='user']")
    public WebElement inputUserName;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement inputPassword;

    @FindBy(xpath = "//select[@name='gid']")
    public WebElement selectGroup;

    @FindBy(xpath = "//input[@name='name']")
    public WebElement inputFullName;

    @FindBy(xpath = "//input[@name='email']")
    public WebElement inputEmail;

    @FindBy(xpath = "//*[@id='usrBY']")
    public WebElement selectYear;

    @FindBy(xpath = "//*[@id='usrBM']")
    public WebElement selectMonth;

    @FindBy(xpath = "//*[@id='usrBD']")
    public WebElement selectDay;

    @FindBy(xpath = "//select[@name='gender']")
    public WebElement selectGender;

    @FindBy(xpath = "//*[@id='rank']")
    public WebElement selectRank;

    @FindBy(xpath = "//*[@id='subbutsetup']")
    public WebElement buttonSaveUser;
    
    
    public UcozPanelPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        open(PageUrl.PANEL);
    }

    public void closePopup() {
        saveClick(closePopup);
    }

    public void clickUsersMenu() {
        saveClick(usersMenu);
    }

    public void searchUser(String username) {
        searchInput.clear();
        searchInput.sendKeys(username);
        saveClick(buttonSearch);
    }

    public void clickAddUserButton() {
        saveClick(addUserButton);
    }

    public void randomFillUserForm() {
        String[] groups = {"Пользователи", "Проверенные", "Модераторы", "Администраторы", "Друзья", "Заблокированные"};
        String[] months = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        String[] genders = {"Мужской", "Женский"};
        String[] ranks = {"Рядовой", "Сержант", "Лейтенант", "Майор", "Подполковник", "Полковник", "Генерал-майор",
                "Генерал-лейтенант", "Генерал-полковник", "Генералиссимус"};

        Random random = new Random();

        String username = UsernameGenerator.generateUsername(4 + random.nextInt(10));
        String password = SecurePasswordGenerator.generatePassword(4 + random.nextInt(10));
        String gid = groups[random.nextInt(groups.length)];
        String fullName = FullNameGenerator.generateFullName();
        String email = username + "@mail.ru";
        String year = String.valueOf(1950 + random.nextInt(70));
        String month = months[random.nextInt(months.length)];
        String day = String.valueOf(1 + random.nextInt(28));
        String gender = genders[random.nextInt(genders.length)];
        String rank = ranks[random.nextInt(ranks.length)];
        
        fillUserForm(username, password, gid, fullName, email, year, month, day, gender, rank);
    }

    public void fillUserForm(String username,
                             String password,
                             String gid,
                             String fullName,
                             String email,
                             String year,
                             String month,
                             String day,
                             String gender,
                             String rank) {
        safeSetInputField(inputUserName, username);
        safeSetInputField(inputPassword, password);

        safeSelectDropdown(selectGroup, gid);

        safeSetInputField(inputFullName, fullName);
        safeSetInputField(inputEmail, email);

        safeSelectDropdown(selectYear, year);
        safeSelectDropdown(selectMonth, month);
        safeSelectDropdown(selectDay, day);
        safeSelectDropdown(selectGender, gender);
        safeSelectDropdown(selectRank, rank);
    }

    public void clickSaveUserButton() {
        saveClick(buttonSaveUser);
    }


}