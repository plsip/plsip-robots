package ai.makeitright.tests.jobs.singlecheckdetailsofjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.jobs.DisplayedJobs;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.jobs.JobsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.testplans.TestPlansPage;
import ai.makeitright.pages.workflows.CreateJobModalWindow;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.ApiMethods;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CheckDetailsOfJob5Test extends DriverConfig {

    private String apiCodeVersion;
    private String apiProcessID;
    private String apiTokenPassword;
    private String apiTokenServer;
    private String apiWorkflowID;
    private String argumentsCollection;
    private String jobID;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String workflowName;


    @BeforeTest
    public void before() {
        apiCodeVersion = System.getProperty("inputParameters.apiCodeVersion");
        apiProcessID = System.getProperty("inputParameters.apiProcessID");
        apiTokenPassword = System.getProperty("secretParameters.apiTokenPassword");
        apiTokenServer = System.getProperty("inputParameters.apiTokenServer");
        apiWorkflowID = System.getProperty("inputParameters.apiWorkflowID");
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
        Main.channel = System.getProperty("inputParameters.channel");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.taskname = pfGlossary + ": TC - Jobs - Check details of job [P20Ct-5]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        workflowName = System.getProperty("inputParameters.workflowOrTestPlanName");
    }

    @Test
    public void checkDetailsOfJob() {
        Main.report.logInfo("********Before test - create new job");
        ApiMethods.createNewJob(apiWorkflowID,apiCodeVersion, apiProcessID, apiTokenPassword, apiTokenServer);
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
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
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

        Main.report.logPass("*********Job was created");
        Main.report.logInfo("*********Start test");

        leftMenu.openPageBy("Jobs");

        JobsPage jobsPage = new JobsPage(driver);
        jobsPage.filterJob(jobID);

        DisplayedJobs displayedJobs = jobsPage.getJobsTable().getJobsFirstRowData();
        Assert.assertNotNull(displayedJobs, "There is no job with ID: '" + jobID + "'");

        Assert.assertEquals(displayedJobs.getWorkflowName(),workflowName,
                "The name of the job's workflow is not right: " + displayedJobs.getWorkflowName());
        Main.report.logPass("Job's workflow name has right value: " + workflowName);

        Assert.assertEquals(displayedJobs.getCreatedBy(),new TopPanel(driver).getCreatedBy(),
                "The value 'Created by' for job which has ID: " + jobID + " is not like expected");
        Main.report.logPass("The job has right value for 'Created by': " + displayedJobs.getCreatedBy());
        Main.report.logInfo("The status of the job is: " + displayedJobs.getStatus().substring(displayedJobs.getStatus().lastIndexOf(" ")+1));

        jobDetailsPage = jobsPage.clickFoundJob(jobID);
//        Assert.assertEquals(jobDetailsPage.getJobStatus(),displayedJobs.getStatus(),
//                "The job status should be: " + displayedJobs.getStatus());
//        Main.report.logPass("The job status is correctly displayed: " + jobDetailsPage.getJobStatus());
        Main.report.logInfo("In the details of job status is : " + jobDetailsPage.getJobStatus().substring(jobDetailsPage.getJobStatus().lastIndexOf(" ")+1));

        Assert.assertEquals(jobDetailsPage.getJobID(),jobID,
                "The value of JOB ID is incorrect: " + jobDetailsPage.getJobID());
        Main.report.logPass("In the job details there is a correct job ID value displayed: " + jobID);

        Assert.assertEquals(jobDetailsPage.getJobHeader(),jobHeader,
                "The job header is not correct: " + jobDetailsPage.getJobHeader());
        Main.report.logPass("In the job details there is a correct job header displayed: " + jobHeader);

        Assert.assertTrue(jobDetailsPage.checkButtonDeleteJobIsEnabled(),
                "Button 'Delete' should be visible and enabled");
        Main.report.logPass("Button 'Delete' is visible and enabled");

        if(!(jobDetailsPage.getJobStatus().substring(jobDetailsPage.getJobStatus().lastIndexOf(" ")+1).equals("Executing")) &&
                (jobDetailsPage.getJobStatus().substring(jobDetailsPage.getJobStatus().lastIndexOf(" ")+1).equals("Creating")) &&
                (jobDetailsPage.getJobStatus().substring(jobDetailsPage.getJobStatus().lastIndexOf(" ")+1).equals("Pending"))) {
            Assert.assertTrue(jobDetailsPage.checkButtonRerunJobIsEnabled(),
                    "Button 'Rerun Job' should be visible and enabled");
            Main.report.logPass("Button 'Rerun Job' is visible and enabled");
        }

        Assert.assertTrue(jobDetailsPage.checkCreatedBy(),
                "Value for 'CREATED BY' in 'Information' section should be the same as on the top of page");
        Main.report.logPass("Value for 'CREATED BY' in 'Information' section is the same as in top panel");

        if(pfGlossary.equals("TA")) {
            Assert.assertTrue(jobDetailsPage.checkTestPlanInformationIsVisible(),
                    "The 'Test plan information' section should be present");
            Main.report.logPass("The 'Test plan information' section is displayed");
        } else {
            Assert.assertTrue(jobDetailsPage.checkWorkflowInformationIsVisible(),
                    "The 'Workflow information' section should be present");
            Main.report.logPass("The 'Workflow information' section is displayed");
        }

        Assert.assertTrue(jobDetailsPage.checkJiraIntegrationIsVisible(),
                "The 'Jira integration' section should be present");
        Main.report.logPass("The 'Jira integration' section is displayed");

        Assert.assertTrue(jobDetailsPage.checkResultsButtonExist(),
                "'Show results' button should be visible and clickable");
        Main.report.logPass("'Show results' button is visible and clickable");
        Main.report.logPass("**********Test has been completed successfully!");
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("jobID", jobID);
        System.setProperty("output", obj.toString());
    }
}
