package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateNewWorkflowModalWindow extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create test plan']")
    private WebElement btnCreateTestPlan;

    @FindBy(xpath = "//button//span[text()='Create workflow']")
    private WebElement btnCreateWorkflow;

    @FindBy(xpath = "//label[@for='type-parallel']")
    private WebElement chboxParallel;

    @FindBy(xpath = "//label[@for='type-sequential']")
    private WebElement chboxSequential;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inpWorkflowName;

    private String workflowName;
    private String workflowType;

    public CreateNewWorkflowModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return wait8ForVisibilityOf(inpWorkflowName);
    }

    public WorkflowDetailsPage clickCreateTestPlanButton(String pfGlossary) {
        click(btnCreateTestPlan, "'Create test plan' button");
        return new WorkflowDetailsPage(driver, pfGlossary);
    }

    public WorkflowDetailsPage clickCreateWorkflowButton(String pfGlossary) {
        click(btnCreateWorkflow, "'Create workflow' button");
        return new WorkflowDetailsPage(driver, pfGlossary);
    }

    public CreateNewWorkflowModalWindow clickWorkflowTypeCheckbox(String workflowType) {
        this.workflowType = workflowType;
        switch(workflowType) {
            case "Parallel":
                click(chboxParallel, "checkbox 'Parallel'");
                break;
            case "Sequential":
                click(chboxSequential, "checkbox 'Sequential'");
                break;
            default:
                Main.report.logFail("Argument for workflow type must be 'Parallel' or 'Sequential'");
        }
        return this;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public CreateNewWorkflowModalWindow setWorkflowName(String workflowName, String workflowType) {
        this.workflowName = workflowName + Methods.getDateTime("yyyyMMddHHmmss") + workflowType;
        sendText(inpWorkflowName, this.workflowName, "input element 'Workflow name'");
        return this;
    }
}
