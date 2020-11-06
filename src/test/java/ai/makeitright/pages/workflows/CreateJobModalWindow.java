package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.schedules.TriggerDetailsPage;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDate;
import java.util.List;

public class CreateJobModalWindow extends BasePage {

    @FindBy(xpath = "//div[@class='flex']/span[1]")
    private WebElement windowHeader;

    @FindBy(xpath = "//div[@class='Polaris-ButtonGroup']/div/button[@class='Polaris-Button Polaris-Button--primary']")
    private WebElement btnSaveAndGo;

    @FindBy(xpath = "//input[@name='SearchArgument']")
    private WebElement inpGlobalArguments;

    @FindAll(
            @FindBy(xpath = "//div[@class='Polaris-ResourceItem__Container']")
    )
    private List<WebElement> collectionTableRows;

    @FindBy(xpath = "//div[@class='Polaris-ResourceItem__Container']//span[@class='Polaris-Checkbox']")
    private WebElement collectionCheckbox;

    @FindBy(xpath = "//div[@class='Polaris-Stack__Item Polaris-Stack__Item--fill']/span")
    private WebElement jobID;

    @FindBy(xpath = "//div[@class='Polaris-Stack__Item Polaris-Stack__Item--fill']/span//parent::div")
    private WebElement alertMsg;

    @FindBy(xpath = "//span[text()='Collection:']//following-sibling::span")
    private WebElement collectionName;

    @FindBy(xpath = "//label[@for='orderType-scheduled']/span/span")
    private WebElement radioBtnScheduled;

    @FindBy(xpath = "//label[@for='orderRepeatRate-daily']/span/span")
    private WebElement radioBtnDaily;

    @FindBy(xpath = "//label[@for='orderRepeatRate-weekly']/span/span")
    private WebElement radioBtnWeekly;

    @FindBy(xpath = "//label[@for='orderRepeatRate-monthly']/span/span")
    private WebElement radioBtnMonthly;

    @FindBy(xpath = "//input[@name='executionDate']")
    private WebElement inpExecutionDate;

    @FindBy(xpath = "//input[@name='endAt']")
    private WebElement inpFinishDate;

    @FindBy(xpath = "//input[@name='executionTime']")
    private WebElement inpExecutionTime;

    @FindAll(
            @FindBy(xpath = "//div[@role='row']")
    )
    private List<WebElement> rowsOfDays;

    @FindBy(xpath = "//button[contains(@aria-label, 'Show next month')]/span")
    private WebElement btnNextMonth;

    @FindBy(xpath = "//div[@class='flex']")
    private WebElement modalWindowHeader;

    public CreateJobModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        waitForVisibilityOf(windowHeader);
        Assertions.assertTrue(windowHeader.getText().contains("Create new job"));
        return true;
    }

    public CreateJobModalWindow clickSaveAndGoToCollectionButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Save and go to Arguments collection' button");
        return this;
    }

    public CreateJobModalWindow chooseGlobalArgumentsCollection(String collection) {
        waitShortForVisibilityOf(inpGlobalArguments);
        sendText(inpGlobalArguments, collection,"input for filter global arguments collection");
        waitForVisibilityOfAllElements(collectionTableRows);
        if (collectionTableRows.size() == 1) {
            click(collectionCheckbox, collection + " collection checkbox");
            return this;
        }
        else {
            Main.report.logFail("The applied filter didn't find the collection: " + collection);
            return null;
        }
    }

    public boolean checkIfCorrectCollectionIsDisplayed(String name) {
        return collectionName.getText().equals(name);
    }

    public CreateJobModalWindow clickSaveAndGoToValuesButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Save and go to Arguments values' button");
        return this;
    }

    public CreateJobModalWindow clickSaveAndGoToScheduleButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Save and go to Schedule' button");
        return this;
    }

    public CreateJobModalWindow clickCreateTriggerRadioButton() {
        waitForVisibilityOf(radioBtnScheduled);
        click(radioBtnScheduled, "radio button to schedule a job");
        return this;
    }

    public CreateJobModalWindow clickRadioBtnDaily() {
        click(radioBtnDaily, "radio button to repeat the job daily");
        return this;
    }

    public CreateJobModalWindow clickRadioBtnWeekly() {
        click(radioBtnWeekly, "radio button to repeat the job weekly");
        return this;
    }

    public CreateJobModalWindow clickRadioBtnMonthly() {
        click(radioBtnMonthly, "radio button to repeat the job monthly");
        return this;
    }

    public CreateJobModalWindow clickExecutionDateInput() {
        waitForClickable(inpExecutionDate);
        click(inpExecutionDate, "input field to choose execution date");
        return this;
    }

    public CreateJobModalWindow clickFinishDateInput() {
        waitForClickable(inpFinishDate);
        click(inpFinishDate, "input field to choose finish date");
        return this;
    }

    public CreateJobModalWindow setExecutionTimeInput(String time) {
        waitForClickable(inpExecutionTime);
        sendText(inpExecutionTime, time,"input field (execution time)");
        return this;
    }

    public CreateJobModalWindow chooseFirstDayOfNextMonth() {
        waitForVisibilityOfAllElements(rowsOfDays);
        click(btnNextMonth, "'Next month' arrow'");
        waitForVisibilityOfAllElements(rowsOfDays);
        rowsOfDays.get(0).findElement(By.xpath(".//button[text()='1']")).click();
        Main.report.logPass("The first day of the next month was chosen");
        return this;
    }

    public CreateJobModalWindow chooseTheNextDay(String day) {
        waitForVisibilityOfAllElements(rowsOfDays);
        for (WebElement row : rowsOfDays) {
            try {
                row.findElement(By.xpath(".//button[text()='" + day + "']")).click();
                Main.report.logPass("The following day was found in calendar: " + day);
                return this;
            } catch (NoSuchElementException e) {
                Main.report.logInfo("Couldn't find the day in the row, try to find in the next row.");
            }
        }
        return this;
    }

    public CreateJobModalWindow clickCreateJobButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Create job' button");
        return this;
    }

    public CreateJobModalWindow clickCreateTriggerButton() {
        waitForClickable(btnSaveAndGo);
        click(btnSaveAndGo, "'Create trigger' button");
        return this;
    }

    public JobDetailsPage clickGoToJobDetailsButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Go to the job details' button");
        return new JobDetailsPage(driver);
    }

    public TriggerDetailsPage clickGoToTriggerDetailsButton() {
        waitForVisibilityOf(btnSaveAndGo);
        click(btnSaveAndGo, "'Go to the trigger details' button");
        return new TriggerDetailsPage(driver);
    }

    public String getCreatedJobID() {
        return jobID.getText();
    }

    public String getPopUpValue() {
        return alertMsg.getText();
    }

    public boolean checkPopUpValue(String msg) {
        return alertMsg.getText().equals(msg);
    }

    public boolean checkModalWindowHeader(String msg) {
        return modalWindowHeader.getText().equals(msg);
    }
}
