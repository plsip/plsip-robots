package ai.makeitright.tests.jobs.createnewjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.workflows.CreateJobModalWindow;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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

    @Before
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
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Workflows");
        }

        WorkflowsPage workflowsPage = new WorkflowsPage(driver);

        CreateJobModalWindow createJobModalWindow = workflowsPage.clickCreateJobButton(workflowName);
        createJobModalWindow
                .clickSaveAndGoToCollectionButton()
                .chooseGlobalArgumentsCollection(argumentsCollection)
                .clickSaveAndGoToValuesButton();
        Assertions.assertTrue(createJobModalWindow.checkIfCorrectCollectionIsDisplayed(argumentsCollection),
                "An incorrect collection was selected.");
        Main.report.logPass("The correct collection was chosen: " + argumentsCollection);

        createJobModalWindow
                .clickSaveAndGoToScheduleButton()
                .clickCreateJobButton();

        jobID = createJobModalWindow.getCreatedJobID();
        Assertions.assertEquals("Your job (ID: " + jobID + ") was successfully created!", createJobModalWindow.getPopUpValue(),
                "The popup has the wrong text: " + createJobModalWindow.getPopUpValue());
        Main.report.logPass("The popup after creating the job has the correct text: " + createJobModalWindow.getPopUpValue());

        JobDetailsPage jobDetailsPage = createJobModalWindow.clickGoToJobDetailsButton();
        Assertions.assertEquals(jobID, jobDetailsPage.getJobID(),
                "The value of JOB ID is incorrect: " + jobDetailsPage.getJobID());
        Main.report.logPass("In the job details there is a correct job ID value displayed: " + jobID);

        jobHeader = "Job " + jobID + " - " + workflowName;
        Assertions.assertEquals(jobHeader, jobDetailsPage.getJobHeader(),
                "The job header is not correct: " + jobDetailsPage.getJobHeader());
        Main.report.logPass("In the job details there is a correct job header displayed: " + jobHeader);
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", "Create a new job");
        obj.put("jobID", jobID);
        obj.put("workflowName", workflowName);
        obj.put("jobHeader", jobHeader);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}



