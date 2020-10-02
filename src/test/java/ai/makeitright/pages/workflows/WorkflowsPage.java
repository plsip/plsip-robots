package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkflowsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create new workflow']")
    private WebElement btnCreateNewWorkflow;

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

    public WorkflowsTable getWorkflowsTable() {
        return new WorkflowsTable(driver);
    }

    public CreateJobModalWindow clickCreateJobButton(final String workflowName) {
        WebElement btnCreateJob = getWorkflowsTable().getDesirableRow(workflowName).findElement(By.xpath(".//td/button"));
        click(btnCreateJob, "'Create job' button");
        return new CreateJobModalWindow(driver);
    }
}
