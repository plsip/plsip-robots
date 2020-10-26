package ai.makeitright.tests.jobs.checkdetailsofjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.jobs.DisplayedJobs;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.jobs.JobsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CheckDetailsOfJobTest extends DriverConfig {

    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String taskname;
    private String workflowName;
    private String jobID;
    private String jobHeader;


    @Before
    public void before() {
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        taskname = System.getProperty("previousResult.taskname");
        workflowName = System.getProperty("previousResult.workflowName");
        jobID = System.getProperty("previousResult.jobID");
        jobHeader = System.getProperty("previousResult.jobHeader");
    }

    @Test
    public void checkDetailsOfJob() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Jobs");

        JobsPage jobsPage = new JobsPage(driver);
        jobsPage.filterJob(jobID);

        DisplayedJobs displayedJobs = jobsPage.getJobsTable().getJobsFirstRowData();
        Assertions.assertNotNull(displayedJobs, "There is no job with ID: '" + jobID + "'");

        Assertions.assertEquals(workflowName, displayedJobs.getWorkflowName(),
                "The name of the job's workflow is not right: " + displayedJobs.getWorkflowName());
        Main.report.logPass("Job's workflow name has right value: " + workflowName);

        Assertions.assertEquals(new TopPanel(driver).getCreatedBy(), displayedJobs.getCreatedBy(),
                "The value 'Created by' for job which has ID: " + jobID + " is not like expected");
        Main.report.logPass("The job has right value for 'Created by': " + displayedJobs.getCreatedBy());
        Main.report.logInfo("The status of the job is: " + displayedJobs.getStatus());

        JobDetailsPage jobDetailsPage = jobsPage.clickFoundJob(jobID);
        Assertions.assertEquals(displayedJobs.getStatus(), jobDetailsPage.getJobStatus(),
                "The job status should be: " + displayedJobs.getStatus());
        Main.report.logPass("The job status is correctly displayed: " + jobDetailsPage.getJobStatus());

        Assertions.assertEquals(jobID, jobDetailsPage.getJobID(),
                "The value of JOB ID is incorrect: " + jobDetailsPage.getJobID());
        Main.report.logPass("In the job details there is a correct job ID value displayed: " + jobID);

        Assertions.assertEquals(jobHeader, jobDetailsPage.getJobHeader(),
                "The job header is not correct: " + jobDetailsPage.getJobHeader());
        Main.report.logPass("In the job details there is a correct job header displayed: " + jobHeader);

        Assertions.assertTrue(jobDetailsPage.checkButtonDeleteJobIsEnabled(),
                "Button 'Delete' should be visible and enabled");
        Main.report.logPass("Button 'Delete' is visible and enabled");

        Assertions.assertTrue(jobDetailsPage.checkButtonRerunJobIsEnabled(),
                "Button 'Rerun Job' should be visible and enabled");
        Main.report.logPass("Button 'Rerun Job' is visible and enabled");

        Assertions.assertTrue(jobDetailsPage.checkCreatedBy(),
                "Value for 'CREATED BY' in 'Information' section should be the same as on the top of page");
        Main.report.logPass("Value for 'CREATED BY' in 'Information' section is the same as in top panel");

        Assertions.assertTrue(jobDetailsPage.checkWorkflowInformationIsVisible(),
                "The 'Workflow information' section should be present");
        Main.report.logPass("The 'Workflow information' section is displayed");

        Assertions.assertTrue(jobDetailsPage.checkJiraIntegrationIsVisible(),
                "The 'Jira integration' section should be present");
        Main.report.logPass("The 'Jira integration' section is displayed");

        Assertions.assertTrue(jobDetailsPage.checkResultsButtonExist(),
                "'Show results' button should be visible and clickable");
        Main.report.logPass("'Show results' button is visible and clickable");

    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskname + " || Check details of job");
        obj.put("jobID", jobID);
        obj.put("workflowName", workflowName);
        obj.put("jobHeader", jobHeader);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}
