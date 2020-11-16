package ai.makeitright.tests.jobs.singlecreatenewjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.testplans.TestPlansPage;
import ai.makeitright.pages.workflows.CreateJobModalWindow;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateNewJobTest extends DriverConfig {

    //from configuration
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String workflowName;
    private String argumentsCollection;

    //for reporting:
    private String jobID;

    @BeforeTest
    public void before() {
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
        Main.channel = System.getProperty("inputParameters.channel");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.taskname = pfGlossary + ": TC - Jobs - Create job immediately [P20Ct-83]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        workflowName = System.getProperty("inputParameters.workflowOrTestPlanName");
    }

    @Test
    public void createNewJob() {
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        CreateJobModalWindow createJobModalWindow = null;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
            TestPlansPage testPlandPage = new TestPlansPage(driver);
            createJobModalWindow = testPlandPage.clickCreateJobButton(workflowName);
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Workflows");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver);
            createJobModalWindow = workflowsPage.clickCreateJobButton(workflowName);
        }
        Assert.assertNotNull(createJobModalWindow,"Modal window for creating job was not open");
        createJobModalWindow
                .clickSaveAndGoToCollectionButton()
                .chooseGlobalArgumentsCollection(argumentsCollection)
                .clickSaveAndGoToValuesButton();
        Assert.assertTrue(createJobModalWindow.checkIfCorrectCollectionIsDisplayed(argumentsCollection),
                "An incorrect collection was selected.");
        Main.report.logPass("The correct collection was chosen: " + argumentsCollection);

        createJobModalWindow
                .clickSaveAndGoToScheduleButton()
                .clickCreateJobButton();

        jobID = createJobModalWindow.getCreatedJobID();
        Assert.assertEquals(createJobModalWindow.getPopUpValue(),"Your job (ID: " + jobID + ") was successfully created!",
                "The popup has the wrong text: " + createJobModalWindow.getPopUpValue());
        Main.report.logPass("The popup after creating the job has the correct text: " + createJobModalWindow.getPopUpValue());

        JobDetailsPage jobDetailsPage = createJobModalWindow.clickGoToJobDetailsButton();
        Assert.assertEquals(jobDetailsPage.getJobID(),jobID,
                "The value of JOB ID is incorrect: " + jobDetailsPage.getJobID());
        Main.report.logPass("In the job details there is a correct job ID value displayed: " + jobID);

        String jobHeader = "Job " + jobID + " - " + workflowName;
        Assert.assertEquals(jobDetailsPage.getJobHeader(),jobHeader,
                "The job header is not correct: " + jobDetailsPage.getJobHeader());
        Main.report.logPass("In the job details there is a correct job header displayed: " + jobHeader);
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("jobID", jobID);
        System.setProperty("output", obj.toString());
    }
}
