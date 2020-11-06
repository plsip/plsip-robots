package ai.makeitright.tests.jobs.checkdetailsoftrigger;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedules.DisplayedTriggers;
import ai.makeitright.pages.schedules.SchedulePage;
import ai.makeitright.pages.schedules.TriggerDetailsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalTime;

public class CheckDetailsOfTriggerTest extends DriverConfig {

    private String pfOrganizationCardName;
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

    @BeforeTest
    public void before() {
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
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
    public void checkDetailsOfTrigger() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Schedule");

        SchedulePage schedulePage = new SchedulePage(driver);
        schedulePage.filterTrigger(workflowName);

        DisplayedTriggers displayedTriggers = schedulePage.getTriggersTable().getTriggersRowData(triggerID);
        Assert.assertNotNull(displayedTriggers, "There is no trigger with ID: '" + triggerID + "'");

        Assert.assertEquals(displayedTriggers.getWorkflowName(),workflowName,
                "The name of the trigger's workflow is not right: " + displayedTriggers.getWorkflowName());
        Main.report.logPass("Trigger's workflow name has right value: " + workflowName);

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                String trgDetailsInScheduleTable = "Everyday at " + LocalTime.NOON.toString() + ",";
                Assert.assertEquals(displayedTriggers.getTriggerDetails(),trgDetailsInScheduleTable,
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + trgDetailsInScheduleTable);
                break;
            case "weekly":
                trgDetailsInScheduleTable = "Every " + Methods.getNameOfNextDay() + " at " + LocalTime.NOON.toString() + ",";
                Assert.assertEquals(displayedTriggers.getTriggerDetails(),trgDetailsInScheduleTable,
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + trgDetailsInScheduleTable);
                break;
            case "monthly":
                trgDetailsInScheduleTable = Methods.getOrdinalIndicatorOfNextDay() + " of every month at " + LocalTime.NOON.toString() + ",";
                Assert.assertEquals(displayedTriggers.getTriggerDetails(),trgDetailsInScheduleTable,
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + trgDetailsInScheduleTable);
                break;
            case "never":
                Assert.assertEquals(displayedTriggers.getTriggerDetails(),triggerDetails + ",",
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + triggerDetails);
                break;
        }

        if (executionFrequency.toLowerCase().equals("never")) {
            Assert.assertEquals(displayedTriggers.getFinishDate(),"N/A",
                    "The 'FINISH DATE' section displays the wrong date: " + displayedTriggers.getFinishDate());
            Main.report.logPass("The 'FINISH DATE' section displays the correct value: N/A");
        }
        else {
            Assert.assertEquals(displayedTriggers.getFinishDate(),finishDate,
                    "The 'FINISH DATE' section displays the wrong date: " + displayedTriggers.getFinishDate());
            Main.report.logPass("The 'FINISH DATE' section displays the correct date: " + finishDate);
        }

        Assert.assertEquals(displayedTriggers.getNextRun(),nextRun,
                "The 'Next run' displays the wrong date: " + displayedTriggers.getNextRun());
        Main.report.logPass("The 'Next run' displays the correct date: " + nextRun);

        TriggerDetailsPage triggerDetailsPage = schedulePage.clickFoundTrigger(triggerID);
        Assert.assertEquals(triggerDetailsPage.getTriggerID(),triggerID,
                "The value of TRIGGER ID is incorrect: " + triggerDetailsPage.getTriggerID());
        Main.report.logPass("In the trigger details there is a correct trigger ID value displayed: " + triggerID);

        Assert.assertEquals(triggerDetailsPage.getTriggerHeader(),workflowName,
                "The trigger header is not correct: " + triggerDetailsPage.getTriggerHeader());
        Main.report.logPass("In the trigger details there is a correct trigger header displayed: " + workflowName);

        Assert.assertTrue(triggerDetailsPage.checkCreatedBy(),
                "Value for 'CREATED BY' in section 'Information' should be the same as on the top of page");
        Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as in top panel: " + triggerDetailsPage.getCreatedBy());

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
        Main.report.logPass("Test has been completed successfully!");
    }


    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskname + " || Check details of trigger");
        obj.put("triggerID", triggerID);
        obj.put("workflowName", workflowName);
        obj.put("executionFrequency", executionFrequency);
        obj.put("triggerDetails", triggerDetails);
        obj.put("nextRun", nextRun);
        System.setProperty("output", obj.toString());
    }
}
