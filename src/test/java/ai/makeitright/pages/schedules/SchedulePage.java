package ai.makeitright.pages.schedules;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

    @FindBy(xpath = "//span[text()='Delete trigger']")
    private WebElement btnDeleteTrigger;

    @Override
    protected boolean isAt() {
        Assert.assertTrue(waitForBlueCircleDisappear());
        return jobsHeader.getText().equals("Schedules");
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
        if (trigger != null) {
            click(trigger, "trigger which has ID: " + triggerID);
            return new TriggerDetailsPage(driver);
        }
        else {
            Main.report.logFail("Couldn't click the trigger which has ID: " + triggerID);
            return null;
        }
    }

    public boolean checkIfScheduleTableIsDisplayed() {
        return driver.findElement(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody")) == null;
    }

    public boolean checkIfTriggerIsDisplayed(final String triggerID) {
        WebElement trigger = getTriggersTable().getDesirableRow(triggerID);
        return trigger != null;
    }

    public SchedulePage clickPauseTriggerButton(final String triggerID) {
        try {
            WebElement btnPauseTrigger = getTriggersTable().getDesirableRow(triggerID).findElement(By.xpath(".//span[text()='Pause trigger']"));
            click (btnPauseTrigger, "'Pause trigger' button of the trigger which has ID: " + triggerID);
        } catch (NoSuchElementException e) {
            Main.report.logInfo("There is no possibility to pause the trigger as it's already paused");
        } catch (Exception e) {
            Assert.fail("The trigger which has ID: " + triggerID + " doesn't exist");
        }
        return this;
    }

    public SchedulePage clickDeleteTriggerButton(final String triggerID) {
        WebElement btnDelete = getTriggersTable().getDesirableRow(triggerID).findElement(By.xpath(".//span[text()='Delete']"));
        waitForClickable(btnDelete);
        click (btnDelete, "'Delete' button of the trigger which has ID: " + triggerID);
        waitForBlueCircleDisappear();
        return this;
    }

    public SchedulePage confirmDeletionOfTrigger() {
        waitForClickable(btnDeleteTrigger);
        click (btnDeleteTrigger, "'Delete trigger' button to confirm the deletion");
        waitForBlueCircleDisappear();
        return this;
    }
}
