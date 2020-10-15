package ai.makeitright.tests.jobs.checkdetailsoftrigger;

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

import java.io.IOException;
import java.time.LocalTime;

public class CheckDetailsOfTriggerTest extends DriverConfig {

    private String pfCompanyName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String taskname;
    private String workflowName;
    private String executionFrequency;
    private String triggerID;
    private String triggerDetails;
    private String nextRun;
    private String finishDate;

    @Before
    public void before() {
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        taskname = System.getProperty("previousResult.taskname");
        workflowName = System.getProperty("previousResult.workflowName");
        triggerID = System.getProperty("previousResult.triggerID");
        executionFrequency = System.getProperty("previousResult.executionFrequency");
        triggerDetails = System.getProperty("previousResult.triggerDetails");
        nextRun = System.getProperty("previousResult.nextRun");
        finishDate = System.getProperty("previousResult.finishDate");
    }

    @Test
    public void checkDetailsOfTrigger() throws IOException {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfCompanyName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Schedule");

        SchedulePage schedulePage = new SchedulePage(driver);
        schedulePage.filterTrigger(workflowName);

        DisplayedTriggers displayedTriggers = schedulePage.getTriggersTable().getTriggersRowData(triggerID);
        Assertions.assertNotNull(displayedTriggers, "There is no trigger with ID: '" + triggerID + "'");

        Methods.getWebScreenShot(driver, "schedulepage");

        Assertions.assertEquals(workflowName, displayedTriggers.getWorkflowName(),
                "The name of the trigger's workflow is not right: " + displayedTriggers.getWorkflowName());
        Main.report.logPass("Trigger's workflow name has right value: " + workflowName);

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                String trgDetailsInScheduleTable = "Everyday at " + LocalTime.NOON.toString() + ",";
                Assertions.assertEquals(trgDetailsInScheduleTable, displayedTriggers.getTriggerDetails(),
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + trgDetailsInScheduleTable);
                break;
            case "weekly":
                trgDetailsInScheduleTable = "Every " + Methods.getNameOfNextDay() + " at " + LocalTime.NOON.toString() + ",";
                Assertions.assertEquals(trgDetailsInScheduleTable, displayedTriggers.getTriggerDetails(),
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + trgDetailsInScheduleTable);
                break;
            case "monthly":
                trgDetailsInScheduleTable = Methods.getOrdinalIndicatorOfNextDay() + " of every month at " + LocalTime.NOON.toString() + ",";
                Assertions.assertEquals(trgDetailsInScheduleTable, displayedTriggers.getTriggerDetails(),
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + trgDetailsInScheduleTable);
                break;
            case "never":
                Assertions.assertEquals(triggerDetails + ",", displayedTriggers.getTriggerDetails(),
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + triggerDetails);
                break;
        }

        if (executionFrequency.toLowerCase().equals("never")) {
            Assertions.assertEquals("N/A", displayedTriggers.getFinishDate(),
                    "The 'FINISH DATE' section displays the wrong date: " + displayedTriggers.getFinishDate());
            Main.report.logPass("The 'FINISH DATE' section displays the correct value: N/A");
        }
        else {
            Assertions.assertEquals(finishDate, displayedTriggers.getFinishDate(),
                    "The 'FINISH DATE' section displays the wrong date: " + displayedTriggers.getFinishDate());
            Main.report.logPass("The 'FINISH DATE' section displays the correct date: " + finishDate);
        }

        Assertions.assertEquals(nextRun, displayedTriggers.getNextRun(),
                "The 'Next run' displays the wrong date: " + displayedTriggers.getNextRun());
        Main.report.logPass("The 'Next run' displays the correct date: " + nextRun);

        TriggerDetailsPage triggerDetailsPage = schedulePage.clickFoundTrigger(triggerID);
        Methods.getWebScreenShot(driver, "details_of_trigger");
        Assertions.assertEquals(triggerID, triggerDetailsPage.getTriggerID(),
                "The value of TRIGGER ID is incorrect: " + triggerDetailsPage.getTriggerID());
        Main.report.logPass("In the trigger details there is a correct trigger ID value displayed: " + triggerID);

        Assertions.assertEquals(workflowName, triggerDetailsPage.getTriggerHeader(),
                "The trigger header is not correct: " + triggerDetailsPage.getTriggerHeader());
        Main.report.logPass("In the trigger details there is a correct trigger header displayed: " + workflowName);

        Assertions.assertTrue(triggerDetailsPage.checkCreatedBy(),
                "Value for 'CREATED BY' in section 'Information' should be the same as on the top of page");
        Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as in top panel: " + triggerDetailsPage.getCreatedBy());

        Assertions.assertTrue(triggerDetailsPage.checkButtonPauseTriggerIsEnabled(),
                "Button 'Pause trigger' should be visible and enabled");
        Main.report.logPass("Button 'Pause trigger' is visible and enabled");

        Assertions.assertEquals(nextRun, triggerDetailsPage.getNextRun(),
                "The 'Next run' section displays the wrong date: " + triggerDetailsPage.getNextRun());
        Main.report.logPass("The 'NEXT RUN' section displays the correct date: " + nextRun);

        Assertions.assertEquals(triggerDetails, triggerDetailsPage.getTriggerDetails(),
                "The trigger details display the wrong date: " + triggerDetailsPage.getTriggerDetails());
        Main.report.logPass("The trigger details display the correct date: " + triggerDetails);

        if (!executionFrequency.toLowerCase().equals("never")) {
            Assertions.assertEquals(finishDate + " 23:59", triggerDetailsPage.getFinishDate(),
                    "The 'FINISH DATE' section displays the wrong date: " + triggerDetailsPage.getFinishDate());
            Main.report.logPass("The 'FINISH DATE' section displays the correct date: " + finishDate);
        }
        Main.report.logPass("Test has been completed successfully!");
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskname + " || Check details of trigger");
        obj.put("triggerID", triggerID);
        obj.put("workflowName", workflowName);
        obj.put("executionFrequency", executionFrequency);
        obj.put("triggerDetails", triggerDetails);
        obj.put("nextRun", nextRun);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}
