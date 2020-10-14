package ai.makeitright.pages.schedules;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SchedulePage extends BasePage {

    @FindBy(xpath = "//h1[@class='Polaris-DisplayText Polaris-DisplayText--sizeLarge']")
    private WebElement jobsHeader;

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr")
    )
    private List<WebElement> tableRows;

    @Override
    protected boolean isAt() {
        waitForBlueCircleDisappear();
        return jobsHeader.getText().equals("Schedule");
    }

    public SchedulePage(final WebDriver driver) {
        super(driver);
    }

    public SchedulePage filterTrigger(String triggerID) {
        Main.report.logInfo("Search trigger which has ID: '" + triggerID + "'");
        sendText(inpFilterItems, triggerID, "input element 'Filter items'");
        waitForBlueCircleDisappear();
        return this;
    }

    public ScheduleTable getTriggersTable() {
        return new ScheduleTable(driver);
    }

    public TriggerDetailsPage clickFoundTrigger(final String triggerID) {
        WebElement trigger = getTriggersTable().getDesirableRow(triggerID);
        click(trigger, "found trigger which has ID: " + triggerID);
        return new TriggerDetailsPage(driver);
    }
}
