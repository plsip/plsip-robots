package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WorkflowsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create new test plan']")
    private WebElement btnCreateNewTestPlan;

    @FindBy(xpath = "//button//span[text()='Create new workflow']")
    private WebElement btnCreateNewWorkflow;

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

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

    public boolean checkIfOneRowDisplayed() {
        try {
            waitShort.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//table[@class='Polaris-DataTable__Table']/tbody/tr"), 1));
            Main.report.logPass("On the workflows table is visible one row");
        } catch (Exception e) {
            Main.report.logFail("Error whilest check if on the workflows table is visible only one row");
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


}
