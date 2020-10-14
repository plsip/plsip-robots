package ai.makeitright.pages.users;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UsersPage extends BasePage {

    @FindBy(xpath = "//main/div/div/div[1]//button[not(@disabled)]")
    private WebElement btnInviteNewUser;

    @FindBy(xpath = "//h1[text()='Users']")
    private WebElement txtUsers;

    @FindAll(
        @FindBy(xpath = "//div[@class='users-list-container']//ul[@class='Polaris-ResourceList']/li")
    )
    private List<WebElement> lstUserRow;

    @Override
    protected boolean isAt() {
        return btnInviteNewUser.isDisplayed();
    }

    public UsersPage(final WebDriver driver) {
        super(driver);
    }

    public boolean checkButtonInviteNewUserIsEnabled() {
        return btnInviteNewUser.isDisplayed();
    }

    public boolean isUserRowDisplayed() {
        return lstUserRow.size() > 0;
    }

    public boolean isUsersTextDisplayed() {
        return txtUsers.isDisplayed();
    }
}
