package ai.makeitright.tests.jobs.displaydetailsofprocess6;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.jobs.DisplayedJobs;
import ai.makeitright.pages.jobs.DisplayedTasks;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.jobs.JobsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.TaskDetailsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DisplayDetailsOfTaskIncludedInJob6 extends DriverConfig {

    private String jobID;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        jobID = System.getProperty("inputParameters.6jobID");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        if (pfGlossary.equals("TA")) {
            Main.taskname = pfGlossary + ": TC - Jobs - Display details of test included in job [P20Ct-6]";
        } else {
            Main.taskname = pfGlossary + ": TC - Jobs - Display details of task included in job [P20Ct-6]";
        }
    }

    @Test
    public void displayDetailsOfTask() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Jobs");

        JobsPage jobsPage = new JobsPage(driver);
        jobsPage.filterJob(jobID);

        Main.report.logInfo("Check if only one row was searched");
        Assert.assertTrue(jobsPage.checkIfOneJobIsDisplayed(),"There's not visible only one row of job with the specified ID");
        Main.report.logPass("One row is displayed in the Users table");

        DisplayedJobs displayedJobs = jobsPage.getJobsTable().getJobsFirstRowData();
        Main.report.logInfo("Go to details of job '" + jobID + "'");
        JobDetailsPage jobDetailsPage = jobsPage.clickJobID(displayedJobs.getLnkID(),jobID);
        DisplayedTasks displayedTasks = jobDetailsPage.getJobsDetailsTasksTable().getTasksFirstRowData();

        TaskDetailsPage taskDetailsPage = jobDetailsPage.clickLnkName(displayedTasks.getLnkName(),displayedTasks.getName());
        Assert.assertTrue(taskDetailsPage.checkListOfCommitsIsDisplayed(),"The list of commits wasn't loaded");

        Assert.assertEquals(taskDetailsPage.getName(),displayedTasks.getName(),"The name of task is not was expected");

    }

    @AfterTest
    public void after() {
        TopPanel topPanel = new TopPanel(driver);
        topPanel.clickTopPanelButton()
                .clickLogOutLink();
    }
}
