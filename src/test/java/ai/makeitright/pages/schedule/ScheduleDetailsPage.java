package ai.makeitright.pages.schedule;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ScheduleDetailsPage extends BasePage {


    @FindBy(xpath = "//div[@class='Polaris-Header-Title']/h1")
    private WebElement txtHeader;

    @FindBy(xpath = "//span[text()='TRIGGER ID']/following-sibling::p/strong")
    private WebElement txtTriggerID;

    @Override
    protected boolean isAt() {
        return waitShortForTextToBePresentInElement(txtHeader,urlOrParam);
    }

    public ScheduleDetailsPage(final WebDriver driver) {
        super(driver);
    }

    public ScheduleDetailsPage(WebDriver driver, String header) {
        super(driver, header);
    }

    public String getCreatedScheduleID() {
        Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
        return txtTriggerID.getText();
    }
}
