package ai.makeitright.pages.login;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//button[contains(@class,'primary')]")
    private WebElement btnSignIn;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement inpEmail;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement inpPassword;

    public LoginPage(final WebDriver driver, final String url, final String param) {
        super(driver, url, param);
    }

    @Override
    protected boolean isAt() {
        Main.report.logInfo("Current URL address: " + driver.getCurrentUrl());
        if (!urlOrParam.equals(driver.getCurrentUrl())) {
            Main.report.logFail("Current URL is not like expected " + urlOrParam);
        }
        Assert.assertEquals(urlOrParam, driver.getCurrentUrl(),
                "Current URL address '" + driver.getCurrentUrl() + "' is not like expected '" + urlOrParam + "'");
        return true;
    }

    public LoginPage setEmailInput(final String email) {
        waitForVisibilityOf(inpEmail);
        sendText(inpEmail, email, "input element 'E-mail'");
        return this;
    }

    public LoginPage setPasswordInput(final String password) {
        sendSecretlyText(inpPassword, password, "input element 'Password'");
        return this;
    }

    public LeftMenu clickSignInButton() {
        click(btnSignIn, "button 'Sign in'");
        OrganizationSelectionPage organizationSelectionPage = new OrganizationSelectionPage(driver);
        organizationSelectionPage.clickSignInButton(param);
        return new LeftMenu(driver);
    }

}
