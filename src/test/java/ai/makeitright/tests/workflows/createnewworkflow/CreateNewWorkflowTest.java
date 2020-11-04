package ai.makeitright.tests.workflows.createnewworkflow;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.workflows.CreateNewWorkflowModalWindow;
import ai.makeitright.pages.workflows.WorkflowDetailsPage;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CreateNewWorkflowTest extends DriverConfig {

    //from configuration
    private String email;
    private String password;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String powerFarmUrl;
    private String workflowName;
    private String workflowType;


    @Before
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        workflowType = System.getProperty("inputParameters.workflowType");
    }

    @Test
    public void createNewWorkflow() {

        driver.get(powerFarmUrl);

        LoginPage loginPage = new LoginPage(driver, powerFarmUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);
        LeftMenu leftMenu = loginPage.clickSignInButton();

        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Workflows");
        }

        WorkflowsPage workflowsPage = new WorkflowsPage(driver);
        CreateNewWorkflowModalWindow createNewWorkflowModalWindow = workflowsPage.clickCreateNewWorkflowButton();

        createNewWorkflowModalWindow
                .setWorkflowName(workflowName,workflowType)
                .clickWorkflowTypeCheckbox(workflowType);
        WorkflowDetailsPage workflowDetailsPage = createNewWorkflowModalWindow.clickCreateWorkflowButton();

        workflowName = createNewWorkflowModalWindow.getWorkflowName();

        Assertions.assertTrue(workflowDetailsPage.checkWorkflowName(workflowName), "In the details of new workflow name of workflow has wrong value");
        Main.report.logPass("In the details of workflow name of workflow is right: '" + workflowName + "'");
        Assertions.assertTrue(workflowDetailsPage.checkButtonCreateJobIsEnabled(), "Button 'Create Job' should be visible and not enable");
        Main.report.logPass("Button 'Create Job' is visible and disabled");
        Assertions.assertTrue(workflowDetailsPage.checkCreatedBy(), "Value for 'CREATED BY' in section 'Information' should be the same as on the top of page");
        Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as in top panel");
        Assertions.assertTrue(workflowDetailsPage.checkWorkflowType(workflowType), "Value for 'TYPE' is section 'Information' should be '" + workflowType + "'");
        Main.report.logPass("Value for 'TYPE' is section 'Information' is right: '" + workflowType + "'");
        Assertions.assertTrue(workflowDetailsPage.checkButtonAddTasksToTheWorkflowIsDisplayed(), "Button 'Add tasks to the workflow' is not displaying");
        Main.report.logPass("Button 'Add tasks to the workflow' is displaying");

    }

    @After
    public void prepareJson() {
        String taskname = "Create new " + workflowType + " workflow";
        JSONObject obj = new JSONObject();
        obj.put("workflowName", workflowName);
        obj.put("workflowType", workflowType);
        obj.put("taskname", taskname);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}
