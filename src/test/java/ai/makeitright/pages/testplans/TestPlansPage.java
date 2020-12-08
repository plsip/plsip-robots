package ai.makeitright.pages.testplans;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.workflows.CreateJobModalWindow;
import ai.makeitright.pages.workflows.CreateNewWorkflowModalWindow;
import ai.makeitright.pages.workflows.WorkflowsTable;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TestPlansPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create new test plan']")
    private WebElement btnCreateNewTestPlan;

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

    @Override
    protected boolean isAt() {
        return btnCreateNewTestPlan.isDisplayed();
    }

    public TestPlansPage(final WebDriver driver) {
        super(driver);
    }

    public CreateJobModalWindow clickCreateJobButton(final String workflowName) {
        filterTestPlan(workflowName);
        WebElement btnCreateJob = getTestPlansTable().getDesirableRow(workflowName).findElement(By.xpath(".//td/button/span"));
        waitForClickable(btnCreateJob);
        click(btnCreateJob, "'Create job' button");
        return new CreateJobModalWindow(driver);
    }

    public CreateNewWorkflowModalWindow clickCreateNewWorkflowButton() {
        click(btnCreateNewTestPlan, "button 'Create new test plan'");
        return new CreateNewWorkflowModalWindow(driver);
    }

    public TestPlansPage filterTestPlan(String workflowName) {
        Main.report.logInfo("Search workflow named: '" + workflowName + "'");
        sendText(inpFilterItems, workflowName, "input element 'Filter items'");
        waitForWhiteSmallCircleDisappear();
        return this;
    }

    public WorkflowsTable getTestPlansTable() {
        return new WorkflowsTable(driver);
    }
}
