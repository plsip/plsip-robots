package ai.makeitright.tests.schedule.checkdetailsoftrigger106;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedule.*;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalTime;

public class CheckDetailsOfTrigger106Test extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Schedule - Create new Schedule Trigger [P20Ct-106]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
    }

    @Test
    public void checkDetailsOfTrigger() {
        Main.report.logInfo("********Before test - create new schedule trigger");
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Schedule");

        SchedulePage schedulePage = new SchedulePage(driver,pfSignInUrl);
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

        Main.report.logInfo("Check if only one row was searched");
        Assert.assertTrue(schedulePage.checkIfOneTriggerIsDisplayed(),"There's not visible only one row of trigger with the specified ID");
        Main.report.logPass("One row is displayed in the Schedule table");

        DisplayedTriggers displayedTriggers = schedulePage.getTriggersTable().getTriggersRowData(triggerID);
        Assert.assertNotNull(displayedTriggers, "There is no trigger with ID: '" + triggerID + "'");

        TriggerDetailsPage triggerDetailsPage = schedulePage.clickFoundTrigger(triggerID);
        Assert.assertEquals(triggerDetailsPage.getTriggerID(),triggerID,
                "The value of TRIGGER ID is incorrect: " + triggerDetailsPage.getTriggerID());
        Main.report.logPass("In the trigger details there is a correct trigger ID value displayed: " + triggerID);

        Assert.assertEquals(triggerDetailsPage.getTriggerHeader(), allScheduleName,
                "The trigger header is not correct: " + triggerDetailsPage.getTriggerHeader());
        Main.report.logPass("In the trigger details there is a correct trigger's header displayed: " + allScheduleName);

        Assert.assertTrue(triggerDetailsPage.checkCreatedBy(),
                "Value for 'CREATED BY' in section 'Information' should be the same as name of user at top panel");
        Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as name of user at top panel: " + triggerDetailsPage.getCreatedBy());

        Assert.assertTrue(triggerDetailsPage.checkButtonPauseTriggerIsEnabled(),
                "Button 'Pause trigger' should be visible and enabled");
        Main.report.logPass("Button 'Pause trigger' is visible and enabled");

        Assert.assertEquals(triggerDetailsPage.getNextRun(),nextRun,
                "The 'Next run' section displays the wrong date: " + triggerDetailsPage.getNextRun());
        Main.report.logPass("The 'NEXT RUN' section displays the correct date: " + nextRun);

        Assert.assertEquals(triggerDetailsPage.getTriggerDetails(),triggerDetails,
                "The trigger details display the wrong date: " + triggerDetailsPage.getTriggerDetails());
        Main.report.logPass("The trigger details display the correct date: " + triggerDetails);

        if (!executionFrequency.toLowerCase().equals("never")) {
            Assert.assertEquals(triggerDetailsPage.getFinishDate(),finishDate + " 23:59",
                    "The 'FINISH DATE' section displays the wrong date: " + triggerDetailsPage.getFinishDate());
            Main.report.logPass("The 'FINISH DATE' section displays the correct date: " + finishDate);
        }
        Main.report.logPass("**********Test has been completed successfully!");
        Main.report.logInfo("*********Delete trigger");
        try {
            driver.get(pfSignInUrl);
            leftMenu.openPageBy("Schedule");

            schedulePage = new SchedulePage(driver);
            schedulePage
                    .filterTrigger(triggerID)
                    .clickPauseTriggerButton(triggerID)
                    .clickDeleteTriggerButton(triggerID)
                    .confirmDeletionOfTrigger();
        } catch(Exception e) {
            Main.report.logInfo("Deleting of trigger was not done properly.");
        }
    }
}
