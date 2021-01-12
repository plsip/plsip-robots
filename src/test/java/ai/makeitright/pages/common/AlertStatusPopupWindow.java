package ai.makeitright.pages.common;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AlertStatusPopupWindow extends BasePage {
    @FindBy(xpath = "//div[@class='Polaris-Banner__Ribbon']/span")
    private WebElement imgBanner;

    @FindBy(xpath = "//div[@class='Polaris-Banner__Content']/p/span")
    private WebElement txtMessageAlert;

    @FindBy(xpath = "//div[@class='Polaris-Banner__Content']/p")
    private WebElement txtMessageAlert2;

    @FindBy(xpath = "//p[@class='Polaris-Heading']")
    private WebElement txtStatus;

    private WebDriverWait wait = new WebDriverWait(driver, 50);

    public AlertStatusPopupWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        try {
            Assert.assertTrue(waitForVisibilityOf(txtStatus));
            return true;
        } catch (Exception e) {
            Main.report.logFail("There is no visible alert status on popup window");
            return false;
        }
    }

    public boolean isBannerRibbon(String banner) {
        try {
            String bannerAttributeClass = imgBanner.getAttribute("class");
            if (bannerAttributeClass.contains(banner)) {
                Main.report.logPass("Popup window has banner ribbon with color '" + banner + "'");
                return true;
            } else {
                Main.report.logFail("Popup window has not expected banner ribbon with color '" + banner + "'. Different color you can read from attribute: '" + bannerAttributeClass + "'");
                return false;
            }

        } catch (NullPointerException e) {
            Main.report.logFail("There was no banner ribbon");
            return false;
        }
    }

    public boolean isAlertStatus(String expectedStatus) {
        try {
            String statusAlert = txtStatus.getText();
            if (statusAlert.equals(expectedStatus)) {
                Main.report.logPass("Popup window has alert status: '" + statusAlert + "'");
                return true;
            } else {
                Main.report.logFail("Popup window has not expected alert status: '" + statusAlert + "' with message '" + txtMessageAlert.getText() + "'");
                return false;
            }

        } catch (NullPointerException e) {
            Main.report.logFail("There was no alert status");
            return false;
        }
    }

    public boolean isAlertMessage(String message) {
        try {
            String messageAlert = txtMessageAlert.getText();
//            if (messageAlert.replaceAll("\\s+", "").contains(message.replaceAll("\\s+", ""))) {
//                Main.report.logPass("Popup window has expected alert message: '" + messageAlert + "'");
//                return true;
//            } else {
//                Main.report.logFail("Popup window has not expected alert message: '" + messageAlert + "' which doesn't contains '" + message + "'");
//                return false;
//            }
            if (messageAlert.equals(message)) {
                Main.report.logPass("Popup window has expected alert message: '" + messageAlert + "'");
                return true;
            } else {
                Main.report.logFail("Popup window has not expected alert message: '" + messageAlert + "', which doesn't contain: '" + message + "'");
                return false;
            }
        } catch (Exception e) {
            Main.report.logFail("There was no message on alert window: " + e.getMessage());
            return false;
        }
    }

    public boolean isAlertMessage2(String message) {
        try {
            String messageAlert = txtMessageAlert2.getText();
            if (messageAlert.replaceAll("\\s+", "").contains(message.replaceAll("\\s+", ""))) {
                Main.report.logPass("Popup window has expected alert message: '" + messageAlert + "'");
                return true;
            } else {
                Main.report.logFail("Popup window has not expected alert message: '" + messageAlert + "' which doesn't contains '" + message + "'");
                return false;
            }
        } catch (Exception e) {
            Main.report.logFail("There was no message on alert window: " + e.getMessage());
            return false;
        }
    }
    public boolean waitForAlertWindowDisappear() {
        return wait.until(ExpectedConditions.invisibilityOf(txtStatus));
    }
}
