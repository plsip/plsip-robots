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

    @FindBy(xpath = "//span[text()='TRIGGER DETAILS']/following-sibling::p")
    private WebElement triggerDetails;

    @FindBy(xpath = "//span[text()='NEXT RUN']/following-sibling::p")
    private WebElement nextRun;

    @FindBy(xpath = "//span[text()='FINISH DATE']/following-sibling::p")
    private WebElement finishDate;

    @FindBy(xpath = "//span[text()='CREATED BY']/following-sibling::p")
    private WebElement txtCreatedBy;

    @FindBy(xpath = "//button//span[text()='Pause trigger']")
    private WebElement btnPauseTriggerEnabled;

    public TriggerDetailsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        waitForBlueCircleDisappear();
        waitForVisibilityOf(btnPauseTriggerEnabled);
        return triggerHeader.isDisplayed();
    }

    public String getTriggerID() {
        return triggerID.getText();
    }

    public String getTriggerDetails() {
        return triggerDetails.getText();
    }

    public String getNextRun() {
        return nextRun.getText();
    }

    public String getFinishDate() {
        return finishDate.getText();
    }

    public boolean checkCreatedBy() {
        return (new TopPanel(driver).getCreatedBy()).equals(txtCreatedBy.getText());
    }

    public String getCreatedBy() {
        return txtCreatedBy.getText();
    }

    public String getTriggerHeader() {
        return triggerHeader.getText();
    }

    public boolean checkButtonPauseTriggerIsEnabled() {
        return btnPauseTriggerEnabled.isEnabled();
    }
}
