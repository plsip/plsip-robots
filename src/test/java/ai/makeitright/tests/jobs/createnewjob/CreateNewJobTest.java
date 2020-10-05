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
    private String pfCompanyName;
    private String powerFarmUrl;
    private String workflowName;
    private String argumentsCollection;

    //for reporting:
    private String jobID;

    @Before
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
    }

    @Test
    public void createNewJob() {
        driver.get(powerFarmUrl);

        LoginPage loginPage = new LoginPage(driver, powerFarmUrl, pfCompanyName);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Workflows");

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
        Assertions.assertTrue(createJobModalWindow.getPopUpValue().equals("Your job (ID: " + jobID + ") was successfully created!"),
                "The popup has the wrong text: ");
        Main.report.logPass("The popup after creating the job has the correct text: " + createJobModalWindow.getPopUpValue());

        JobDetailsPage jobDetailsPage = createJobModalWindow.clickGoToJobDetailsButton();
        Assertions.assertTrue(jobDetailsPage.checkJobID(jobID), "The value of JOB ID is incorrect.");
        Main.report.logPass("In the job details there is a correct job ID value displayed: " + jobID);

        Assertions.assertTrue(jobDetailsPage.checkJobHeader("Job " + jobID + " - " + workflowName),
                "The job header is not correct.");
        Main.report.logPass("In the job details there is a correct job header displayed: " + jobDetailsPage.getJobHeader());
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", "Create a new job");
        obj.put("workflowName", workflowName);
        obj.put("jobID", jobID);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}



