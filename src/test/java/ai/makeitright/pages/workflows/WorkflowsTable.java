package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WorkflowsTable extends BasePage {

    @FindAll(
            @FindBy(xpath = "//tbody/tr")
    )
    private List<WebElement> tableRows;

    @FindBy(xpath = "//tbody")
    private WebElement table;

    @FindBy(xpath = "//li[@title='Next Page']/button[not(@disabled)]")
    private WebElement btnRightArrowPagination;

    public WorkflowsTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return table.isDisplayed();
    }

    public WebElement getDesirableRow(final String workflowName) {
        do {
            waitForVisibilityOfAllElements(tableRows);
            if (tableRows.size() > 0) {
                for (WebElement row : tableRows) {
                    String name = row.findElement(By.xpath(".//th/div/div/h2/a/span")).getText();
                    if (name.equals(workflowName)) {
                        Main.report.logPass("The workflow '" + workflowName + "' was found on the platform's 'Workflow' list");
                        return row;
                    }
                }
                try {
                    btnRightArrowPagination.isDisplayed();
                    click(btnRightArrowPagination, "button with right arrow to go to the next page");
                } catch (Exception e) {
                    Main.report.logFail("There was no workflow '" + workflowName + "'");
                    return null;
                }
            } else {
                Main.report.logInfo("There was no workflow '" + workflowName + "'");
                return null;
            }
        } while (true);
    }
}