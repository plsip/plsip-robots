package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.common.TopPanel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WorkflowDetailsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Add tasks to the workflow']")
    private WebElement btnAddTasksToTheWorkflow;

    @FindBy(xpath = "//button//span[text()='Delete Workflow']")
    private WebElement btnDeleteWorkflow;

    @FindBy(xpath = "//button[@disabled]//span[text()='Create job']")
    private WebElement btnCreateJobDisabled;

    @FindBy(xpath = "//span[text()='CREATED BY']/following-sibling::p")
    private WebElement txtCreatedBy;

    @FindBy(xpath = "//span[text()='TYPE']/following-sibling::p")
    private WebElement txtType;

    @FindBy(xpath = "//h1//div[@class='inline-editor-text']")
    private WebElement txtWorkflowName;

    public WorkflowDetailsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return btnDeleteWorkflow.isDisplayed();
    }

    public boolean checkButtonAddTasksToTheWorkflowIsDisplayed() {
        return btnAddTasksToTheWorkflow.isDisplayed();
    }

    public boolean checkButtonCreateJobIsEnabled() {
        return btnCreateJobDisabled.isDisplayed();
    }

    public boolean checkCreatedBy() {
        return (new TopPanel(driver).getCreatedBy()).equals(txtCreatedBy.getText());
    }

    public boolean checkWorkflowName(String workflowName) {
        return workflowName.equals(txtWorkflowName.getText());
    }

    public boolean checkWorkflowType(String workflowType) {
        return workflowType.equals(txtType.getText());
    }

}
