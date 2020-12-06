package ai.makeitright.pages.users;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserDetailsPage extends BasePage {

    @FindBy(xpath = "//input[@name='email' and @disabled]")
    private WebElement inpEmailAddress;

    @FindBy(xpath = "//input[@name='firstName']")
    private WebElement inpFirstName;

    @FindBy(xpath = "//input[@name='lastName']")
    private WebElement inpLastName;

    @FindBy(xpath = "//select[@name='role']/following-sibling::div/span[1]")
    private WebElement inpRole;

    @FindBy(xpath = "//div/span[text()='CREATED']/following-sibling::p")
    private WebElement txtCreatedDate;

    @FindBy(xpath = "//div[@class='Polaris-Header-Title']/h1")
    private WebElement txtUserHeaderTitle;

    @FindBy(xpath = "//h2[text()='User settings']")
    private WebElement txtUserSettings;

    public UserDetailsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return txtUserSettings.isDisplayed();
    }

    public String getCreatedDate() {
        return txtCreatedDate.getText();
    }

    public String getEmailAddress() {
        return inpEmailAddress.getAttribute("value");
    }

    public String getFirstLastName() {
       return inpFirstName.getAttribute("value")+" "+inpLastName.getAttribute("value");
    }

    public String getRole() {
        return inpRole.getText();
    }

    public String getUserNameHeader() {
        return txtUserHeaderTitle.getText();
    }
}
