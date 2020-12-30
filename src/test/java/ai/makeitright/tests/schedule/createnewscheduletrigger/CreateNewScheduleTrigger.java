package ai.makeitright.tests.schedule.createnewscheduletrigger;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedules.CreateNewScheduleTriggerModalWindow;
import ai.makeitright.pages.schedules.ScheduleDetailsPage;
import ai.makeitright.pages.schedules.SchedulePage;
import ai.makeitright.pages.users.UsersPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalTime;

public class CreateNewScheduleTrigger extends DriverConfig {

    //from configuration
    private String executionFrequency;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

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
        Main.taskname = pfGlossary + ": TC - Schedule - Create new Schedule Trigger [P20Ct-85]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
    }
    @Test
    public void createNewJobWithTrigger() {
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Schedule");

        SchedulePage schedulePage = new SchedulePage(driver);
        CreateNewScheduleTriggerModalWindow createNewScheduleTriggerModalWindow = schedulePage.clickCreateNewScheduleTriggerButton();

        createNewScheduleTriggerModalWindow = createNewScheduleTriggerModalWindow
                .setScheduleTriggerName()
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
                createNewScheduleTriggerModalWindow.clickCreateTriggerButton();
                break;
            default:
                Assert.fail("There is a wrong value of 'executionFrequency' parameter");
        }

        createNewScheduleTriggerModalWindow.clickFinishDateInput()
                .chooseFirstDayOfNextMonth();
        ScheduleDetailsPage scheduleDetailsPage = createNewScheduleTriggerModalWindow.clickCreateTriggerButton();

        triggerID = scheduleDetailsPage.getCreatedScheduleID();

        nextRun = Methods.getDateOfNextDay("dd/MM/YYYY") + " " + LocalTime.NOON.toString();
        finishDate = Methods.getFirstDayOfNextMonth();

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                triggerDetails = "Everyday at " +
                        LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth();
                break;
            case "weekly":
                triggerDetails = "Every " + Methods.getNameOfNextDay() + " at " +
                        LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth();
                break;
            case "monthly":
                triggerDetails = Methods.getOrdinalIndicatorOfNextDay() +
                        " of every month at " + LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth();
                break;
            case "never":
                triggerDetails = "At "+ Methods.getDateOfNextDay("dd/MM/YYYY") + " " + LocalTime.NOON.toString();
                finishDate = "N/A";
                break;
        }
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("triggerID", triggerID);
        obj.put("triggerDetails", triggerDetails);
        obj.put("nextRun", nextRun);
        obj.put("finishDate", finishDate);
        System.setProperty("output", obj.toString());
    }
}
