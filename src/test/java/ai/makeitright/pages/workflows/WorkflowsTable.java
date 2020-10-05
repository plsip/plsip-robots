package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WorkflowsTable extends BasePage {

    @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//table[@class='Polaris-DataTable__Table']/tbody/tr")
    )
    private List<WebElement> tableRows;

    public WorkflowsTable(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody")));
        return table.isDisplayed();
    }

    public DisplayedWorkflows getWorkflowsFirstRowData() {
        DisplayedWorkflows displayedWorkflows;
        WebElement row = tableRows.get(0);
        List<WebElement> rowColumns = row.findElements(By.xpath(".//td"));
        displayedWorkflows = new DisplayedWorkflows()
                .setLnkName(row.findElement(By.xpath(".//th//span[1]")))
                .setName(row.findElement(By.xpath(".//th//span")).getText())
                .setCreatedBy(rowColumns.get(CREATEDBY).getText())
                .setDateCreated(rowColumns.get(DATECREATED).getText())
                .setType(rowColumns.get(TYPE).getText());
        return displayedWorkflows;
    }

    private int CREATEDBY = 0;
    private int DATECREATED = 1;
    private int TYPE = 2;
}
