package ai.makeitright.tests.jobs.deletetrigger;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.jobs.DisplayedJobs;
import ai.makeitright.pages.jobs.JobDetailsPage;
import ai.makeitright.pages.jobs.JobsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedules.DisplayedTriggers;
import ai.makeitright.pages.schedules.SchedulePage;
import ai.makeitright.pages.schedules.TriggerDetailsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalTime;

public class DeleteTriggerTest extends DriverConfig {

    private String pfCompanyName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String taskname;
    private String workflowName;
    private String triggerID;

    @Before
    public void before() {
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        taskname = System.getProperty("previousResult.taskname");
        workflowName = System.getProperty("previousResult.workflowName");
        triggerID = System.getProperty("previousResult.triggerID");
    }

    @Test
    public void deleteTrigger() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfCompanyName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Schedule");

        SchedulePage schedulePage = new SchedulePage(driver);
        schedulePage
                .filterTrigger(workflowName)
                .clickPauseTriggerButton(triggerID)
                .clickDeleteTriggerButton(triggerID)
                .confirmDeletionOfTrigger();

        Assertions.assertTrue(!schedulePage.checkIfTriggerIsDisplayed(triggerID),
                "The deleted trigger is still on the trigger list");
        Main.report.logPass("The trigger is no longer on the trigger list");
        Main.report.logPass("Test has been completed successfully!");
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskname + " || Delete created trigger");
        obj.put("triggerID", triggerID);
        obj.put("workflowName", workflowName);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}
