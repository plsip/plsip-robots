package ai.makeitright.tests.workflows.singlecreatenewworkflow;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.workflows.CreateNewWorkflowModalWindow;
import ai.makeitright.pages.workflows.WorkflowDetailsPage;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateNewWorkflow77or78Test extends DriverConfig {

    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String workflowName;
    private String workflowType;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        workflowType = System.getProperty("inputParameters.workflowType");
        if (workflowType.equals("Parallel")) {
            if (pfGlossary.equals("TA")) {
                Main.taskname = pfGlossary + ": TC - Test Plans - Create new " + workflowType + " test plan [P20Ct-77]";
            } else {
                Main.taskname = pfGlossary + ": TC - Workflows - Create new " + workflowType + " workflow [P20Ct-77]";
            }
        } else {
            Main.taskname = pfGlossary + ": TC - Workflows - Create new " + workflowType + " workflow [P20Ct-78]";
        }
        workflowName = System.getProperty("inputParameters.workflowName");
    }

    @Test
    public void createNewWorkflow() {

        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();

        CreateNewWorkflowModalWindow createNewWorkflowModalWindow;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
            createNewWorkflowModalWindow = workflowsPage.clickCreateNewTestPlanButton();
            createNewWorkflowModalWindow
                    .setWorkflowName(workflowName, workflowType);
            if (!(workflowType.equals("Parallel") || pfGlossary.equals("RPA"))) {
                Main.report.logFail("For organization TA you can't create sequential test plan");
            }
            createNewWorkflowModalWindow
                    .clickWorkflowTypeCheckbox(workflowType);
            createNewWorkflowModalWindow.clickCreateTestPlanButton(pfGlossary);
        } else {
            leftMenu.openPageBy("Workflows");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
            createNewWorkflowModalWindow = workflowsPage.clickCreateNewWorkflowButton();
            createNewWorkflowModalWindow
                    .setWorkflowName(workflowName, workflowType);
            if (!(workflowType.equals("Parallel") || pfGlossary.equals("RPA"))) {
                Main.report.logFail("For organization TA you can't create sequential test plan");
            }
            createNewWorkflowModalWindow
                    .clickWorkflowTypeCheckbox(workflowType);
            createNewWorkflowModalWindow.clickCreateWorkflowButton(pfGlossary);
        }
        Assert.assertFalse(createNewWorkflowModalWindow.isFormDisplayedAgain(),"Empty form was displaying again");
        workflowName = createNewWorkflowModalWindow.getWorkflowName();

        WorkflowDetailsPage workflowDetailsPage = new WorkflowDetailsPage(driver,pfGlossary);
        Assert.assertTrue(workflowDetailsPage.checkWorkflowName(workflowName), "In the details of new workflow name of workflow has wrong value");
        Main.report.logPass("In the details of workflow name of workflow is right: '" + workflowName + "'");
        Assert.assertTrue(workflowDetailsPage.checkButtonCreateJobIsEnabled(), "Button 'Create Job' should be visible and not enable");
        Main.report.logPass("Button 'Create Job' is visible and disabled");
        Assert.assertTrue(workflowDetailsPage.checkCreatedBy(), "Value for 'CREATED BY' in section 'Information' should be the same as on the top of page");
        Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as in top panel");
        Assert.assertTrue(workflowDetailsPage.checkWorkflowType(workflowType), "Value for 'TYPE' is section 'Information' should be as in word without number: '" + workflowType + "'");
        Main.report.logPass("Value for 'TYPE' is section 'Information' is right: word from '" + workflowType+ "' but without number");
        if(pfGlossary.equals("TA")) {
            Assert.assertTrue(workflowDetailsPage.checkButtonAddTestsToTheTestPlanIsDisplayed(), "Button 'Add tests to the test plan' is not displaying");
            Main.report.logPass("Button 'Add tasks to the workflow' is displaying");
        } else {
            Assert.assertTrue(workflowDetailsPage.checkButtonAddTasksToTheWorkflowIsDisplayed(), "Button 'Add tasks to the workflow' is not displaying");
            Main.report.logPass("Button 'Add tasks to the workflow' is displaying");
        }

    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("workflowName", workflowName);
        obj.put("workflowType", workflowType);
        System.setProperty("output", obj.toString());
    }

}
