package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginGitLabPage extends BasePage {

    @FindBy(xpath = "//*[@value='Sign in']")
    private WebElement btnSignIn;

    @FindBy(id = "user_login")
    private WebElement inpUsername;

    @FindBy(id = "user_password")
    private WebElement inpPassword;

    public LoginGitLabPage(final WebDriver driver, final String url) {
        super(driver, url);
    }

    @Override
    protected boolean isAt() {
        Main.report.logInfo("Current URL address: " + driver.getCurrentUrl());
        if (!urlOrParam.equals(driver.getCurrentUrl())) {
            Main.report.logFail("Current URL is not like expected " + urlOrParam);
        }
        Assert.assertEquals(driver.getCurrentUrl(),urlOrParam,
                "Current URL address '" + driver.getCurrentUrl() + "' is not like expected '" + urlOrParam + "'");
        return true;
    }

    public LoginGitLabPage setUsernameField(final String username) {
        Assert.assertTrue(waitLongForVisibilityOf(inpUsername),"There is no visible input element for entering username");
        sendText(inpUsername, username, "input element 'Username or email'");
        return this;
    }

    public LoginGitLabPage setPasswordField(final String password) {
        sendSecretlyText(inpPassword, password, "input element 'Password'");
        return this;
    }

    public ProjectsPage clickSignInButton() {
        click(btnSignIn, "button 'Sign in'");
        return new ProjectsPage(driver);
    }

}
