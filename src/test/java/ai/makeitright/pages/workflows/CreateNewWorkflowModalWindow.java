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
import org.testng.Assert;

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

    WebDriverWait wait = new WebDriverWait(driver, 4);

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

    public void clickCreateWorkflowButton(String pfGlossary) {
        click(btnCreateWorkflow, "'Create workflow' button");
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
        Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
        return this;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public boolean isFormDisplayedAgain() {
        try {
            if (wait.until(ExpectedConditions.attributeToBe(inpWorkflowName, "value",""))) {
                Main.report.logFail("There was input element for 'Workflow'/'Test name' without text in it");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public CreateNewWorkflowModalWindow setWorkflowName(String workflowName, String workflowType) {
//        Assert.assertTrue(waitForVisibilityOf(inpWorkflowName),"There was no visible input element 'Workflow name'");
        Assert.assertTrue(waitForClickable(inpWorkflowName),"There was no clickable input element 'Workflow name'");
        this.workflowName = Methods.getDateTime("yyyyMMddHHmmss") + workflowType + workflowName;
//        click(inpWorkflowName, "input element 'Workflow name'");
        new Action(driver).sendText(inpWorkflowName, this.workflowName, "input element 'Workflow name'");
        Main.report.logInfo("Entered: " + inpWorkflowName.getAttribute("value"));
        Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
        if(inpWorkflowName.getAttribute("value").equals("")) {
            Main.report.logFail("Value was empty. Enter again.");
            Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
            Main.report.logInfo("Entered: " + inpWorkflowName.getAttribute("value"));
            click(inpWorkflowName, "input element 'Workflow name'");
            clearAndSendText(inpWorkflowName, this.workflowName, "input element 'Workflow name'");
        }
        Main.report.logInfoWithScreenCapture(Methods.getScreenShotAsBase64(driver));
        return this;
    }
}
