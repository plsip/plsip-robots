package ai.makeitright.pages.schedule;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class CreateNewScheduleTriggerModalWindow extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create Schedule Trigger']")
    private WebElement btnCreateTrigger;

    @FindBy(xpath = "//button[contains(@aria-label, 'Show next month')]/span")
    private WebElement btnNextMonth;

    @FindBy(xpath = "//input[@name='executionDate']")
    private WebElement inpExecutionDate;

    @FindBy(xpath = "//input[@name='executionTime']")
    private WebElement inpExecutionTime;

    @FindBy(xpath = "//input[@name='endAt']")
    private WebElement inpFinishDate;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inpScheduleTriggerName;

    @FindBy(xpath = "//label[@for='orderRepeatRate-daily']/span/span")
    private WebElement radioBtnDaily;

    @FindBy(xpath = "//label[@for='orderRepeatRate-multiple_times_a_day']/span/span")
    private WebElement radioBtnMultipleTimesADay;

    @FindBy(xpath = "//label[@for='orderRepeatRate-never']/span/span")
    private WebElement radioBtnNever;

    @FindBy(xpath = "//label[@for='orderRepeatRate-weekly']/span/span")
    private WebElement radioBtnWeekly;

    @FindBy(xpath = "//label[@for='orderRepeatRate-monthly']/span/span")
    private WebElement radioBtnMonthly;

    @FindAll(
            @FindBy(xpath = "//div[@role='row']")
    )
    private List<WebElement> rowsOfDays;

    private String scheduleName;

    @FindBy(xpath = "//div[@class='Polaris-Modal-Header']//*[text()='Create Schedule Trigger']")
    private WebElement txtHeaderTitle;


    public CreateNewScheduleTriggerModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return waitShortForVisibilityOf(txtHeaderTitle);
    }

    public CreateNewScheduleTriggerModalWindow chooseFirstDayOfNextMonth() {
        waitForVisibilityOfAllElements(rowsOfDays);
        click(btnNextMonth, "'Next month arrow'");
        waitForVisibilityOfAllElements(rowsOfDays);
        rowsOfDays.get(0).findElement(By.xpath(".//button[text()='1']")).click();
        Main.report.logPass("The first day of the next month was chosen");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow chooseTheNextDay(String day) {
        waitForVisibilityOfAllElements(rowsOfDays);
        if (day.equals("1")) {
            click(btnNextMonth, "'Next month arrow'");
        }
        waitForVisibilityOfAllElements(rowsOfDays);
        for (WebElement row : rowsOfDays) {
            try {
                waitForClickable(row.findElement(By.xpath(".//button[text()='" + day + "']")));
                row.findElement(By.xpath(".//button[text()='" + day + "']")).click();
                Main.report.logPass("The following day was found in calendar: " + day);
                return this;
            } catch (NoSuchElementException e) {
                Main.report.logInfo("Couldn't find the day in the row, try to find in the next row.");
            }
        }
        return this;
    }

    public ScheduleDetailsPage clickCreateTriggerButton(String scheduleName) {
        waitForClickable(btnCreateTrigger);
        click(btnCreateTrigger, "'Create trigger' button");
        waitForBlueCircleDisappear();
        return new ScheduleDetailsPage(driver, scheduleName);
    }

    public CreateNewScheduleTriggerModalWindow clickExecutionDateInput() {
        waitForClickable(inpExecutionDate);
        click(inpExecutionDate, "input element 'Execution date'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow clickFinishDateInput() {
        waitForClickable(inpFinishDate);
        click(inpFinishDate, "input element 'Finish date'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow clickRadioBtnDaily() {
        click(radioBtnDaily, "radio button 'Daily'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow clickRadioBtnMonthly() {
        click(radioBtnMonthly, "radio button 'Monthly'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow clickRadioBtnMultipleTimesADay() {
        click(radioBtnMultipleTimesADay, "radio button 'Multiple times a day'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow clickRadioBtnNever() {
        click(radioBtnNever, "radio button 'Never'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow clickRadioBtnWeekly() {
        click(radioBtnWeekly, "radio button 'Weekly'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow setExecutionTime(String time) {
        waitForClickable(inpExecutionTime);
        sendText(inpExecutionTime, time,"input element 'Execution time'");
        return this;
    }

    public CreateNewScheduleTriggerModalWindow setScheduleTriggerName(String scheduleName) {
//        this.scheduleName = Methods.getDateTime("yyyyMMddHHmmss") + scheduleName;
//        sendText(inpScheduleTriggerName,this.scheduleName, "input element 'Schedule Trigger Name'");
//        Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
//        return this.scheduleName;
//        this.scheduleName = Methods.getDateTime("yyyyMMddHHmmss") + scheduleName;
        sendText(inpScheduleTriggerName,scheduleName, "input element 'Schedule Trigger Name'");
        Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
        return this;
    }
}
