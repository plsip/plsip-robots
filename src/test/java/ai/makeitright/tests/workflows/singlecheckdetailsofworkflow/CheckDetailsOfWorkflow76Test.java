package ai.makeitright.tests.workflows.singlecheckdetailsofworkflow;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.workflows.CreateNewWorkflowModalWindow;
import ai.makeitright.pages.workflows.DisplayedWorkflows;
import ai.makeitright.pages.workflows.WorkflowDetailsPage;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CheckDetailsOfWorkflow76Test extends DriverConfig {

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
        taskname = System.getProperty("previousResult.taskname");
        workflowName = System.getProperty("previousResult.workflowName");
        workflowType = System.getProperty("previousResult.workflowType");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        workflowType = System.getProperty("inputParameters.workflowType");
        if(pfGlossary.equals("TA")) {
            Main.taskname = pfGlossary + ": TC - Test Plans - Check details of " + workflowType + " test plan [P20Ct-76]";
        } else {
            Main.taskname = pfGlossary + ": TC - Workflows - Check details of " + workflowType + " workflow [P20Ct-76]";
        }
        workflowName = System.getProperty("inputParameters.workflowName");
    }

    @Test
    public void checkDetailsOfWorkflow() {
        Main.report.logInfo("******************************\nBefore test - create new workflow\n");
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();

        CreateNewWorkflowModalWindow createNewWorkflowModalWindow;
        WorkflowDetailsPage workflowDetailsPage;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
            createNewWorkflowModalWindow = workflowsPage.clickCreateNewTestPlanButton();
            createNewWorkflowModalWindow
                    .setWorkflowName(workflowName, workflowType);
            if ((workflowType.equals("Sequential") && pfGlossary.equals("TA"))) {
                Main.report.logFail("For organization TA you can't create sequential test plan");
            }
            createNewWorkflowModalWindow
                    .clickWorkflowTypeCheckbox(workflowType);
            workflowDetailsPage = createNewWorkflowModalWindow.clickCreateTestPlanButton(pfGlossary);
        } else {
            leftMenu.openPageBy("Workflows");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
            createNewWorkflowModalWindow = workflowsPage.clickCreateNewWorkflowButton();
            createNewWorkflowModalWindow
                    .setWorkflowName(workflowName, workflowType);
            if ((workflowType.equals("Sequential") && pfGlossary.equals("TA"))) {
                Main.report.logFail("For organization TA you can't create sequential test plan");
            }
            createNewWorkflowModalWindow
                    .clickWorkflowTypeCheckbox(workflowType);
            workflowDetailsPage = createNewWorkflowModalWindow.clickCreateWorkflowButton(pfGlossary);
        }

        workflowName = createNewWorkflowModalWindow.getWorkflowName();
        Main.report.logPass("******************************\nWorkflow was created");
        Main.report.logInfo("******************************\nStart test");
        driver.get(pfSignInUrl);
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
            workflowsPage.filterWorkflow(workflowName);
            Assert.assertTrue(workflowsPage.checkIfOneRowDisplayed(),"After filtering tes plan table by name should be visible only one row");

            DisplayedWorkflows displayedWorkflows = workflowsPage.getWorkflowsTable().getWorkflowsFirstRowData();
            Assert.assertNotNull(displayedWorkflows, "There is no test plan with name: '" + workflowName + "'");
            Assert.assertEquals(displayedWorkflows.getName(),workflowName, "The name of test plan is not right");
            Main.report.logPass("Test plan has right value for 'Name'");
            Assert.assertEquals(displayedWorkflows.getCreatedBy(),new TopPanel(driver).getCreatedBy(), "The value 'Created by' wor test plan " + workflowName + " is not like expected");
            Main.report.logPass("Test plan has right value for 'Created by'");
            Assert.assertEquals(displayedWorkflows.getType(),workflowType, "The value 'Type' for test plan " + workflowName + " is not like expected");
            Main.report.logPass("Test plan has right value for 'Type'");

            workflowDetailsPage = workflowsPage.clickWorkflowNameLink(displayedWorkflows.getLnkName(), workflowName, pfGlossary);
            Assert.assertTrue(workflowDetailsPage.checkWorkflowName(workflowName), "In the details of new test plan, name of test plan has wrong value");
            Main.report.logPass("In the details of test plan, name of test plan is right: '" + workflowName + "'");
            Assert.assertTrue(workflowDetailsPage.checkButtonCreateJobIsEnabled(), "Button 'Create Job' should be visible and not enable");
            Main.report.logPass("Button 'Create Job' is visible and disabled");
            Assert.assertTrue(workflowDetailsPage.checkCreatedBy(), "Value for 'CREATED BY' in section 'Information' should be the same as on the top of page");
            Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as in top panel");
            Assert.assertTrue(workflowDetailsPage.checkWorkflowType(workflowType), "Value for 'TYPE' is section 'Information' should be '" + workflowType + "'");
            Main.report.logPass("Value for 'TYPE' is section 'Information' is right: '" + workflowType + "'");
            Assert.assertTrue(workflowDetailsPage.checkButtonAddTestsToTheTestPlanIsDisplayed(), "Button 'Add tests to the test plan' is not displaying");
            Main.report.logPass("Button 'Add tests to the test plan' is displaying");
            Main.report.logPass("**********Test has been completed successfully!");
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Workflows");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
            workflowsPage.filterWorkflow(workflowName);
            Assert.assertTrue(workflowsPage.checkIfOneRowDisplayed(),"After filtering workflow table by name should be visible only one row");

            DisplayedWorkflows displayedWorkflows = workflowsPage.getWorkflowsTable().getWorkflowsFirstRowData();
            Assert.assertNotNull(displayedWorkflows, "There is no workflow with name: '" + workflowName + "'");
            Assert.assertEquals(displayedWorkflows.getName(),workflowName, "The name of workflow is not right");
            Main.report.logPass("Workflow has right value for 'Name'");
            Assert.assertEquals(displayedWorkflows.getCreatedBy(),new TopPanel(driver).getCreatedBy(), "The value 'Created by' wor workflow " + workflowName + " is not like expected");
            Main.report.logPass("Workflow has right value for 'Created by'");
            Assert.assertEquals(displayedWorkflows.getType(),workflowType, "The value 'Type' for workflow " + workflowName + " is not like expected");
            Main.report.logPass("Worklfow has right value for 'Type'");

            workflowDetailsPage = workflowsPage.clickWorkflowNameLink(displayedWorkflows.getLnkName(), workflowName, pfGlossary);
            Assert.assertTrue(workflowDetailsPage.checkWorkflowName(workflowName), "In the details of new workflow name of workflow has wrong value");
            Main.report.logPass("In the details of workflow name of workflow is right: '" + workflowName + "'");
            Assert.assertTrue(workflowDetailsPage.checkButtonCreateJobIsEnabled(), "Button 'Create Job' should be visible and not enable");
            Main.report.logPass("Button 'Create Job' is visible and disabled");
            Assert.assertTrue(workflowDetailsPage.checkCreatedBy(), "Value for 'CREATED BY' in section 'Information' should be the same as on the top of page");
            Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as in top panel");
            Assert.assertTrue(workflowDetailsPage.checkWorkflowType(workflowType), "Value for 'TYPE' is section 'Information' should be '" + workflowType + "'");
            Main.report.logPass("Value for 'TYPE' is section 'Information' is right: '" + workflowType + "'");
            Assert.assertTrue(workflowDetailsPage.checkButtonAddTasksToTheWorkflowIsDisplayed(), "Button 'Add tasks to the workflow' is not displaying");
            Main.report.logPass("Button 'Add tasks to the workflow' is displaying");
            Main.report.logPass("**********Test has been completed successfully!");
        }



    }
}
