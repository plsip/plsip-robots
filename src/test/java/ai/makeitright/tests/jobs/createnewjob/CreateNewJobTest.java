package ai.makeitright.tests.jobs.createnewjob;

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
    private String email;
    private String password;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String powerFarmUrl;
    private String workflowName;
    private String argumentsCollection;

    //for reporting:
    private String jobID;
    private String jobHeader;

    @BeforeTest
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
    }

    @Test
    public void createNewJob() {
        driver.get(powerFarmUrl);

        LoginPage loginPage = new LoginPage(driver, powerFarmUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);

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

        jobHeader = "Job " + jobID + " - " + workflowName;
        Assert.assertEquals(jobDetailsPage.getJobHeader(),jobHeader,
                "The job header is not correct: " + jobDetailsPage.getJobHeader());
        Main.report.logPass("In the job details there is a correct job header displayed: " + jobHeader);
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", "Create a new job");
        obj.put("jobID", jobID);
        obj.put("workflowName", workflowName);
        obj.put("jobHeader", jobHeader);
        System.setProperty("output", obj.toString());
    }
}



