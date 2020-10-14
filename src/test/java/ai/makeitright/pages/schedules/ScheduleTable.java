package ai.makeitright.pages.schedules;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ScheduleTable extends BasePage {

    @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr")
    )
    private List<WebElement> tableRows;

    @FindBy(xpath = "//li[@title='Next Page']/button[not(@disabled)]")
    private WebElement btnRightArrowPagination;

    public ScheduleTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody")));
        return table.isDisplayed();
    }

    public DisplayedTriggers getTriggersRowData(String triggerID) {
        WebElement row = getDesirableRow(triggerID);
        List<WebElement> rowColumns = row.findElements(By.xpath(".//td"));
        DisplayedTriggers displayedTriggers = new DisplayedTriggers()
                .setWorkflowName(rowColumns.get(WORKFLOW_NAME).getText())
                .setTriggerDetails(rowColumns.get(TRIGGER_DETAILS).getText())
                .setNextRun(rowColumns.get(NEXT_RUN).getText())
                .setFinishDate(rowColumns.get(FINISH_DATE).getText());
        return displayedTriggers;
    }

    private final int WORKFLOW_NAME = 0;
    private final int TRIGGER_DETAILS = 1;
    private final int NEXT_RUN = 2;
    private final int FINISH_DATE = 3;

    public WebElement getDesirableRow(final String triggerID) {
        do {
            waitForVisibilityOfAllElements(tableRows);
            if (tableRows.size() > 0) {
                for (WebElement row : tableRows) {
                    String name = row.findElement(By.xpath(".//th/a")).getText();
                    if (name.contains(triggerID)) {
                        Main.report.logPass("The trigger '" + triggerID + "' was found on the platform's 'Schedule' list");
                        return row;
                    }
                }
                try {
                    btnRightArrowPagination.isDisplayed();
                    click(btnRightArrowPagination, "button with right arrow to go to the next page");
                } catch (Exception e) {
                    Main.report.logFail("There was no trigger which has ID '" + triggerID + "'");
                    return null;
                }
            } else {
                Main.report.logInfo("There was no trigger which has ID '" + triggerID + "'");
                return null;
            }
        } while (true);
    }
}