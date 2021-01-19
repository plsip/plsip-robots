package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class WorkflowsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create new test plan']")
    private WebElement btnCreateNewTestPlan;

    @FindBy(xpath = "//button//span[text()='Create new workflow']")
    private WebElement btnCreateNewWorkflow;

    private By getHeadersName(int headerNumber) {
        return new By.ByXPath("//thead/tr/th[" + headerNumber + "]");
    }

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

    @FindAll(
            @FindBy(xpath = "//tbody/tr")
    )
    private List<WebElement> lstWorkflowRow;

    @FindBy(xpath = "//div[@class='Polaris-Header-Title']/h1")
    private WebElement txtHeader;

    private WebDriverWait waitShort = new WebDriverWait(driver, 3);

    @Override
    protected boolean isAt() {
        if (urlOrParam.equals("TA")) {
            return btnCreateNewTestPlan.isDisplayed();
        } else {
            return btnCreateNewWorkflow.isDisplayed();
        }
    }

    public WorkflowsPage(final WebDriver driver) {
        super(driver);
    }

    public WorkflowsPage(final WebDriver driver, String pfGlossary) {
        super(driver, pfGlossary);
    }

    public boolean checkForColumnNumberHeaderHasValue(int headerNumber, String expectedHeaderName) {
        String headersName = driver.findElement(getHeadersName(headerNumber)).getText();
        Main.report.logInfo("Actual headers name is '" + headersName + "'");
        return headersName.equals(expectedHeaderName);
    }

    public boolean checkHeaderIs(String expectedHeader) {
        return expectedHeader.equals(txtHeader.getText());
    }

    public boolean checkIfOneRowDisplayed() {
        try {
            waitShort.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody/tr"), 1));
            Main.report.logPass("On the workflows table is visible one row");
        } catch (Exception e) {
            Main.report.logFail("Error whilest check if on the workflows table is visible only one row");
        }
        return true;
    }

    public boolean checkWorkflowsFromFirstPaginationPageContainCreateJobButton() {
        for (WebElement row : lstWorkflowRow) {
            Assert.assertEquals(row.findElement(By.xpath("./td[4]/button")).getText(),"Create job","Lack of 'Create job' button");
        }
        return true;
    }

    public boolean checkWorkflowsFromFirstPaginationPageContainValuesInFirstFourColumns() {
        for (WebElement row : lstWorkflowRow) {
            Assert.assertNotEquals(row.findElement(By.xpath("./th//span")).getText(),"","In first column row doesn't contain value");
            for (int i=1;i<4;i++) {
                Assert.assertNotEquals(row.findElement(By.xpath("./td["+i+"]")).getText(),"","In " + i + " column row doesn't contain value");
            }
        }
        return true;
    }

    public CreateJobModalWindow clickCreateJobButton(final String workflowName) {
        filterWorkflow(workflowName);
        WebElement btnCreateJob = getWorkflowsTable().getDesirableRow(workflowName).findElement(By.xpath(".//td/button/span"));
        click(btnCreateJob, "'Create job' button");
        return new CreateJobModalWindow(driver);
    }

    public CreateNewWorkflowModalWindow clickCreateNewTestPlanButton() {
        click(btnCreateNewTestPlan, "button 'Create new test plan'");
        return new CreateNewWorkflowModalWindow(driver);
    }

    public CreateNewWorkflowModalWindow clickCreateNewWorkflowButton() {
        click(btnCreateNewWorkflow, "button 'Create new workflow'");
        return new CreateNewWorkflowModalWindow(driver);
    }

    public WorkflowDetailsPage clickWorkflowNameLink(WebElement element, String workflowName, String pfGlossary) {
        click(element, "link with name of workflow '" + workflowName + "'");
        return new WorkflowDetailsPage(driver,pfGlossary);
    }

    public WorkflowsPage filterWorkflow(String workflowName) {
        Main.report.logInfo("Search workflow named: '" + workflowName + "'");
        sendText(inpFilterItems, workflowName, "input element 'Filter items'");
        waitForBlueCircleDisappear();
        return this;
    }

    public WorkflowsTable getWorkflowsTable() {
        return new WorkflowsTable(driver);
    }

    public boolean isWorkflowRowDisplayed() {
        return lstWorkflowRow.size() > 0;
    }

}
