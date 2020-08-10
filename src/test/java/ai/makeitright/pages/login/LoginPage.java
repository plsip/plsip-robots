package ai.makeitright.pages.login;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver, String url) {
        super(driver,url);
    }

    @Override
    protected boolean isAt() {
        Main.report.logInfo("Current URL address: "+driver.getCurrentUrl());
        if(!url.equals(driver.getCurrentUrl())) {
            Main.report.logFail("Current URL is not like expected " + url);
        }
        Assertions.assertEquals(url, driver.getCurrentUrl(),
                "Current URL address '" + driver.getCurrentUrl() + "' is not like expected '" + url + "'");
        return true;
    }

    @FindBy(xpath = "//button[contains(@class,'primary')]")
    private WebElement btnSignIn;

    @FindBy(xpath = "//input[@name='email']")
    private WebElement inpEmail;

    @FindBy(xpath = "//input[@name='password']")
    private WebElement inpPassword;

    public LeftMenu clickButtonSignIn() {
        click(btnSignIn, "button 'Sign in'");
        return new LeftMenu(driver);
    }

    public void setEmail(String email) {
        sendText(inpEmail, email, "input element 'E-mail'");
    }

    public void setPassword(String password) {
        sendSecretlyText(inpPassword, password, "input element 'Password'");
    }
}
