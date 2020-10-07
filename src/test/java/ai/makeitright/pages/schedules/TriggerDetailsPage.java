package ai.makeitright.pages.schedules;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.common.TopPanel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TriggerDetailsPage extends BasePage {

    @FindBy(xpath = "//h1[@class='Polaris-DisplayText Polaris-DisplayText--sizeLarge']")
    private WebElement triggerHeader;

    @FindBy(xpath = "//span[text()='TRIGGER ID']/following-sibling::p/strong")
    private WebElement triggerID;

    public TriggerDetailsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return triggerHeader.isDisplayed();
    }

    public boolean checkTriggerID(String id) {
        return triggerID.getText().equals(id);
    }

    public boolean checkTriggerHeader(String msg) {
        return triggerHeader.getText().equals(msg);
    }

    public String getTriggerHeader() {
        return triggerHeader.getText();
    }
}
