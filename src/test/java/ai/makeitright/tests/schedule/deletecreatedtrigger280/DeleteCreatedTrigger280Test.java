package ai.makeitright.tests.schedule.deletecreatedtrigger280;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedule.CreateNewScheduleTriggerModalWindow;
import ai.makeitright.pages.schedule.ScheduleDetailsPage;
import ai.makeitright.pages.schedule.SchedulePage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalTime;

public class DeleteCreatedTrigger280Test extends DriverConfig {

    //from configuration
    private String executionFrequency;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String scheduleName;

    //for reporting:
    private String finishDate;
    private String nextRun;
    private String triggerDetails;
    private String triggerID;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        executionFrequency = System.getProperty("inputParameters.executionFrequency");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        scheduleName = System.getProperty("inputParameters.scheduleName");
        Main.taskname = pfGlossary + ": TC - Schedule - Create new Schedule Trigger [P20Ct-280]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
    }

    @Test
    public void deleteTrigger() {
        Main.report.logInfo("********Before test - create new schedule trigger");
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Schedule");

        SchedulePage schedulePage = new SchedulePage(driver, pfSignInUrl);
        CreateNewScheduleTriggerModalWindow createNewScheduleTriggerModalWindow = schedulePage.clickCreateNewScheduleTriggerButton();

        String allScheduleName = Methods.getDateTime("yyyyMMddHHmmss") + scheduleName;
        createNewScheduleTriggerModalWindow = createNewScheduleTriggerModalWindow
                .setScheduleTriggerName(allScheduleName)
                .clickExecutionDateInput()
                .chooseTheNextDay(Methods.getNextDayOfMonth())
                .setExecutionTime(LocalTime.NOON.toString());

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                Main.report.logInfo("'Daily' execution frequency has been selected");
                createNewScheduleTriggerModalWindow.clickRadioBtnDaily();
                break;
            case "weekly":
                Main.report.logInfo("'Weekly' execution frequency has been selected");
                createNewScheduleTriggerModalWindow.clickRadioBtnWeekly();
                break;
            case "monthly":
                Main.report.logInfo("'Monthly' execution frequency has been selected");
                createNewScheduleTriggerModalWindow.clickRadioBtnMonthly();
                break;
            case "never":
                Main.report.logInfo("Option 'Never' of execution frequency has been selected");
                createNewScheduleTriggerModalWindow.clickRadioBtnNever();
                break;
            default:
                Assert.fail("There is a wrong value of 'executionFrequency' parameter");
        }

        createNewScheduleTriggerModalWindow.clickFinishDateInput()
                .chooseFirstDayOfNextMonth();
        ScheduleDetailsPage scheduleDetailsPage = createNewScheduleTriggerModalWindow.clickCreateTriggerButton(allScheduleName);

        Main.report.logInfo("Read triggerID of new schedule trigger");
        triggerID = scheduleDetailsPage.getCreatedScheduleID();
        Main.report.logPass("TriggerID: '" + triggerID + "'");

        Main.report.logPass("*********Schedule trigger was created");
        Main.report.logInfo("*********Start test");

        driver.get(pfSignInUrl);
        leftMenu.openPageBy("Schedule");

        schedulePage = new SchedulePage(driver);
        schedulePage.filterTrigger(triggerID);

        Main.report.logInfo("Check if only one row was searched");
        Assert.assertTrue(schedulePage.checkIfOneTriggerIsDisplayed(),"There's not visible only one row of trigger with the specified ID");
        Main.report.logPass("One row is displayed in the Schedule table");

        schedulePage.clickPauseTriggerButton(triggerID)
                .clickDeleteTriggerButton(triggerID)
                .confirmDeletionOfTrigger();

        Assert.assertFalse(schedulePage.checkIfScheduleTableIsDisplayed());
        Main.report.logPass("The trigger is no longer on the trigger list");
        Main.report.logPass("**********Test has been completed successfully!");
    }
}
