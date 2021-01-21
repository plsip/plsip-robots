package ai.makeitright.tests.schedule.createtrigger99;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedule.CreateNewScheduleTriggerModalWindow;
import ai.makeitright.pages.schedule.DisplayedTriggers;
import ai.makeitright.pages.schedule.ScheduleDetailsPage;
import ai.makeitright.pages.schedule.SchedulePage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalTime;

public class CreateTrigger99 extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Schedule - Create new Schedule Trigger [P20Ct-99]";
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

        nextRun = Methods.getDateOfNextDay("dd/MM/YYYY") + " " + LocalTime.NOON.toString();
        finishDate = Methods.getFirstDayOfNextMonth();

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                triggerDetails = "Everyday at " +
                        LocalTime.NOON.toString();
                break;
            case "weekly":
                triggerDetails = "Every " + Methods.getNameOfNextDay() + " at " +
                        LocalTime.NOON.toString();
                break;
            case "monthly":
                triggerDetails = Methods.getOrdinalIndicatorOfNextDay() +
                        " of every month at " + LocalTime.NOON.toString();
                break;
            case "never":
                triggerDetails = "At "+ Methods.getDateOfNextDay("dd/MM/YYYY") + " " + LocalTime.NOON.toString();
                finishDate = "N/A";
                break;
        }

//        leftMenu.openPageBy("Schedule");
        leftMenu.openPageBy("Schedule");

        DisplayedTriggers displayedTriggers = schedulePage.getTriggersTable().getTriggersRowData(triggerID);
        Assert.assertNotNull(displayedTriggers, "There is no trigger with ID: '" + triggerID + "'");

        schedulePage.moveScrollToELement(displayedTriggers.getRow());
        Assert.assertEquals(displayedTriggers.getScheduleTriggerName(),allScheduleName,"'Schedule Trigger Name' has not right value");
        Main.report.logPass("Trigger's name has right value: " + allScheduleName);
        Assert.assertEquals(displayedTriggers.getTriggerDetails(),triggerDetails,"Value for 'Trigger details' columns is not right");
        Assert.assertEquals(displayedTriggers.getRow().findElement(By.xpath(".//td[3]")).getText(),nextRun,"Value for 'Next run' column is not right: ");
        Assert.assertEquals(displayedTriggers.getFinishDate(),finishDate,"Value for 'Finish date' column is not right");

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
