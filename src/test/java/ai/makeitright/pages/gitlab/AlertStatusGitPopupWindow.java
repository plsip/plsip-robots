package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class AlertStatusGitPopupWindow extends BasePage {

    @FindBy(xpath = "//div[@class='flash-container flash-container-page sticky']")
    private WebElement alertStatus;

    public AlertStatusGitPopupWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        try {
            Assert.assertTrue(waitForVisibilityOf(alertStatus));
            return true;
        } catch (Exception e) {
            Main.report.logFail("There is no visible alert status on popup window");
            return false;
        }
    }

    public boolean isAlertStatus(String expectedStatus) {
        try {
            String statusAlert = alertStatus.findElement(By.xpath(".//div/span")).getText();
            if (statusAlert.equals(expectedStatus)) {
                Main.report.logPass("Popup window has alert status: '" + statusAlert + "'");
                return true;
            } else {
                Main.report.logFail("Popup window has alert status: '" + statusAlert +"'\nnot like expected\n" + expectedStatus);
                return false;
            }

        } catch (NullPointerException e) {
            Main.report.logFail("There was no alert status");
            return false;
        }
    }
}
