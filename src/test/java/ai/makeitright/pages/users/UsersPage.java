package ai.makeitright.pages.users;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UsersPage extends BasePage {

    @FindBy(xpath = "//main/div/div/div[1]//button[not(@disabled)]")
    private WebElement btnInviteNewUser;

    @FindBy(xpath = "//input[@placeholder='Filter users']")
    private WebElement inpFilterUsers;

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

    public boolean checkIfOneUserIsDisplayed() {
        return lstUserRow.size() == 1;
    }

    public UserDetailsPage clickUserRow(WebElement lnkUserDetails) {
        click(lnkUserDetails,"row with user to see details");
        return new UserDetailsPage(driver);
    }

    public UsersPage filterUsers(String email) {
        Main.report.logInfo("Search user which has email: '" + email + "'");
        sendText(inpFilterUsers, email, "input element 'Filter users'");
        waitForBlueCircleDisappear();
        return this;
    }

    public UsersTable getUsersTable() {
        return new UsersTable(driver);
    }

    public boolean isUserRowDisplayed() {
        return lstUserRow.size() > 0;
    }

    public boolean isUsersTextDisplayed() {
        return txtUsers.isDisplayed();
    }
}
