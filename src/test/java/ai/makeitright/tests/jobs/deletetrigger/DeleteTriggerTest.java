package ai.makeitright.tests.jobs.deletetrigger;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedules.SchedulePage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteTriggerTest extends DriverConfig {

    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String taskname;
    private String workflowName;
    private String triggerID;

    @BeforeTest
    public void before() {
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        taskname = System.getProperty("previousResult.taskname");
        workflowName = System.getProperty("previousResult.workflowName");
        triggerID = System.getProperty("previousResult.triggerID");
    }

    @Test
    public void deleteTrigger() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
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

        Assert.assertFalse(schedulePage.checkIfTriggerIsDisplayed(triggerID));
        Main.report.logPass("The trigger is no longer on the trigger list");
        Main.report.logPass("Test has been completed successfully!");
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskname + " || Delete created trigger");
        obj.put("triggerID", triggerID);
        obj.put("workflowName", workflowName);
        System.setProperty("output", obj.toString());
    }
}
