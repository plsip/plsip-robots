package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkflowsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create new workflow']")
    private WebElement btnCreateNewWorkflow;

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

    @Override
    protected boolean isAt() {
        return btnCreateNewWorkflow.isDisplayed();
    }

    public WorkflowsPage(final WebDriver driver) {
        super(driver);
    }

    public CreateNewWorkflowModalWindow clickCreateNewWorkflowButton() {
        click(btnCreateNewWorkflow, "button 'Create new workflow'");
        return new CreateNewWorkflowModalWindow(driver);
    }

    public WorkflowDetailsPage clickWorkflowNameLink(WebElement element, String workflowName) {
        click(element, "link with name of workflow '" + workflowName + "'");
        return new WorkflowDetailsPage(driver);
    }

    public WorkflowsPage filterWorkflow(String workflowName) {
        Main.report.logInfo("Search workflow named: '" + workflowName + "'");
        sendText(inpFilterItems, workflowName, "input element 'Filter items'");
        return this;
    }

    public WorkflowsTable getWorkflowsTable() {
        return new WorkflowsTable(driver);
    }
}
