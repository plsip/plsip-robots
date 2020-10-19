package ai.makeitright.pages.tasks;

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
import java.util.concurrent.TimeUnit;

public class TasksTable extends BasePage {

    @FindBy(xpath = "//button[@class='Polaris-Pagination__Button Polaris-Pagination__NextButton' and not(@disabled)]")
    private WebElement btnArrowNext;

    @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr")
    )
    private List<WebElement> tableRows;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr/th")
    )
    private List<WebElement> tableRowsColumns;

    private WebDriverWait wait = new WebDriverWait(driver, 5);

    public TasksTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody")));
        return table.isDisplayed();
    }

    public DisplayedTasks getTasksFirstRowData() {
        DisplayedTasks displayedTasks;
        WebElement row = tableRows.get(0);
        List<WebElement> rowColumns = row.findElements(By.xpath(".//td"));
        displayedTasks = new DisplayedTasks()
                .setLnkName(row.findElement(By.xpath(".//th//span[1]")))
                .setName(row.findElement(By.xpath(".//th//span")).getText())
                .setCreatedBy(rowColumns.get(CREATEDBY).getText())
                .setDateCreated(rowColumns.get(DATECREATED).getText())
                .setTechnology(rowColumns.get(TECHNOLOGY).getText())
                .setLastCommmit(rowColumns.get(LASTCOMMIT).getText());
        return displayedTasks;
    }

    public DisplayedTasks getTasksRowData(String taskName) {
        DisplayedTasks displayedTasks;
        do {
            for (WebElement row : tableRows) {
                if (row.findElement(By.xpath(".//th//span")).getText().equals(taskName)) {
                    List<WebElement> rowColumns = row.findElements(By.xpath(".//tr/td"));
                    displayedTasks = new DisplayedTasks()
                            .setName(row.findElement(By.xpath(".//th//span")).getText())
                            .setCreatedBy(rowColumns.get(CREATEDBY).getText())
                            .setDateCreated(rowColumns.get(DATECREATED).getText())
                            .setTechnology(rowColumns.get(TECHNOLOGY).getText())
                            .setLastCommmit(rowColumns.get(LASTCOMMIT).getText());
                    Main.report.logPass("Task with name " + taskName + " was found on the list of tasks");
                    return displayedTasks;
                }
            }
            try {
                driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
                btnArrowNext.isDisplayed();
                click(btnArrowNext,"arrow '->'");
            } catch (Exception e) {
                Main.report.logInfo("There was no task with name ' " + taskName + "' in table 'Tasks'");
                return null;
            }
        } while(true);

    }

    private int CREATEDBY = 0;
    private int DATECREATED = 1;
    private int TECHNOLOGY = 2;
    private int LASTCOMMIT = 3;

}
