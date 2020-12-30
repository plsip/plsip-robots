package ai.makeitright.pages.schedules;

import ai.makeitright.pages.BasePage;
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
        return txtHeader.getText().equals(urlOrParam);
    }

    public ScheduleDetailsPage(final WebDriver driver) {
        super(driver);
    }

    public ScheduleDetailsPage(final WebDriver driver, String header) {
        super(driver, header);
    }

    public String getCreatedScheduleID() {
        return txtTriggerID.getText();
    }
}
