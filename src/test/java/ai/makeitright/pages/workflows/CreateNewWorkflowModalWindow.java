package ai.makeitright.pages.workflows;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Action;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateNewWorkflowModalWindow extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create test plan']")
    private WebElement btnCreateTestPlan;

    @FindBy(xpath = "//button//span[text()='Create workflow']")
    private WebElement btnCreateWorkflow;

    @FindBy(xpath = "//label[@for='type-parallel']")
    private WebElement chboxParallel;

    @FindBy(xpath = "//label[@for='type-sequential']")
    private WebElement chboxSequential;

    @FindBy(xpath = "//input[@name='name' and @aria-invalid='false']")
    private WebElement inpWorkflowName;

    private String workflowName;
    private String workflowType;

    WebDriverWait wait = new WebDriverWait(driver, 3);

    public CreateNewWorkflowModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return wait8ForVisibilityOf(inpWorkflowName);
    }

    public void clickCreateTestPlanButton(String pfGlossary) {
        click(btnCreateTestPlan, "'Create test plan' button");
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

    public boolean isFormDisplayedAgain() {
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(inpWorkflowName, ""));
            Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
            Main.report.logFail("Empty form was visible");
            return true;
        } catch (Exception e) {
            Main.report.logInfo(e.getMessage());
            return false;
        }
    }

    public CreateNewWorkflowModalWindow setWorkflowName(String workflowName, String workflowType) {
        this.workflowName = workflowName + Methods.getDateTime("yyyyMMddHHmmss") + workflowType;
        new Action(driver).sendText(inpWorkflowName, this.workflowName, "input element 'Workflow name'");
        return this;
    }
}
