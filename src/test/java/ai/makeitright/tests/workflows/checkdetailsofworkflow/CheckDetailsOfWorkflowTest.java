package ai.makeitright.tests.workflows.checkdetailsofworkflow;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.workflows.DisplayedWorkflows;
import ai.makeitright.pages.workflows.WorkflowDetailsPage;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CheckDetailsOfWorkflowTest extends DriverConfig {

    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String taskname;
    private String workflowName;
    private String workflowType;


    @Before
    public void before() {
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        taskname = System.getProperty("previousResult.taskname");
        workflowName = System.getProperty("previousResult.workflowName");
        workflowType = System.getProperty("previousResult.workflowType");
    }

    @Test
    public void checkDetailsOfWorkflow() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Workflows");

        WorkflowsPage workflowsPage = new WorkflowsPage(driver);
        workflowsPage.filterWorkflow(workflowName)
                .checkIfOneRowDisplayed();


        DisplayedWorkflows displayedWorkflows = workflowsPage.getWorkflowsTable().getWorkflowsFirstRowData();
        Assertions.assertNotNull(displayedWorkflows, "There is no workflow with name: '" + workflowName + "'");
        Assertions.assertEquals(workflowName, displayedWorkflows.getName(), "The name of workflow is not right");
        Main.report.logPass("Workflow has right value for 'Name'");
        Assertions.assertEquals(new TopPanel(driver).getCreatedBy(), displayedWorkflows.getCreatedBy(), "The value 'Created by' wor workflow " + workflowName + " is not like expected");
        Main.report.logPass("Workflow has right value for 'Created by'");
        Assertions.assertEquals(workflowType, displayedWorkflows.getType(), "The value 'Type' for workflow " + workflowName + " is not like expected");
        Main.report.logPass("Worklfow has right value for 'Type'");

        WorkflowDetailsPage workflowDetailsPage = workflowsPage.clickWorkflowNameLink(displayedWorkflows.getLnkName(), workflowName);
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
        JSONObject obj = new JSONObject();
        obj.put("workflowName", workflowName);
        obj.put("workflowType", workflowType);
        obj.put("taskname",taskname + " || Check details of workflow");
        System.setProperty("output", obj.toString());
        driver.close();
    }
}
