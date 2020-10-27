package ai.makeitright.pages.jobs;

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

public class JobsTable extends BasePage {

    @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr")
    )
    private List<WebElement> tableRows;

    public JobsTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody")));
        return table.isDisplayed();
    }

    public DisplayedJobs getJobsFirstRowData() {
        WebElement row = tableRows.get(0);
        List<WebElement> rowColumns = row.findElements(By.xpath(".//td"));
        DisplayedJobs displayedJobs = new DisplayedJobs()
                .setWorkflowName(rowColumns.get(WORKFLOWNAME).getText())
                .setStatus(rowColumns.get(STATUS).getText().replace("\n", " "))
                .setCreatedBy(rowColumns.get(CREATEDBY).getText());
        return displayedJobs;
    }


    private final int WORKFLOWNAME = 0;
    private final int STATUS = 4;
    private final int CREATEDBY = 5;

    public WebElement getDesirableRow(final String jobID) {
        do {
            waitForVisibilityOfAllElements(tableRows);
            if (tableRows.size() > 0) {
                for (WebElement row : tableRows) {
                    String name = row.findElement(By.xpath(".//th/a")).getText();
                    if (name.contains(jobID)) {
                        Main.report.logPass("The job '" + jobID + "' was found on the platform's 'Jobs' list");
                        return row;
                    }
                }
            } else {
                Main.report.logInfo("There was no job which has ID '" + jobID + "'");
                return null;
            }
        } while (true);
    }
}