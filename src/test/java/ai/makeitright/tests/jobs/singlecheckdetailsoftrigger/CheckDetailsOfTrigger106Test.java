package ai.makeitright.tests.jobs.singlecheckdetailsoftrigger;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedules.DisplayedTriggers;
import ai.makeitright.pages.schedules.SchedulePage;
import ai.makeitright.pages.schedules.TriggerDetailsPage;
import ai.makeitright.pages.testplans.TestPlansPage;
import ai.makeitright.pages.workflows.CreateJobModalWindow;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalTime;

public class CheckDetailsOfTrigger106Test extends DriverConfig {

    private String argumentsCollection;
    private String executionFrequency;
    private String finishDate;
    private String nextRun;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String triggerDetails;
    private String triggerID;
    private String workflowName;

    @BeforeTest
    public void before() {
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
        Main.channel = System.getProperty("inputParameters.channel");
        executionFrequency = System.getProperty("inputParameters.executionFrequency");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.taskname = pfGlossary + ": TC - Schedule - Check details of the trigger [P20Ct-106]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        workflowName = System.getProperty("inputParameters.workflowOrTestPlanName");
    }

    @Test
    public void checkDetailsOfTrigger() {
        Main.report.logInfo("********Before test - create new triggered job");
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        CreateJobModalWindow createJobModalWindow = null;
        String workflowOrTestPlan = "";
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
            TestPlansPage testPlandPage = new TestPlansPage(driver);
            createJobModalWindow = testPlandPage.clickCreateJobButton(workflowName);
            workflowOrTestPlan = "test plan";
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Workflows");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver);
            createJobModalWindow = workflowsPage.clickCreateJobButton(workflowName);
            workflowOrTestPlan = "workflow";
        }

        Assert.assertNotNull(createJobModalWindow,"Modal window for creating job was not open");
        createJobModalWindow
                .clickSaveAndGoToCollectionButton()
                .chooseGlobalArgumentsCollection(argumentsCollection)
                .clickSaveAndGoToValuesButton();
        Assert.assertTrue(createJobModalWindow.checkIfCorrectCollectionIsDisplayed(argumentsCollection),
                "An incorrect collection was selected.");
        Main.report.logPass("The correct collection was chosen: " + argumentsCollection);

        createJobModalWindow
                .clickSaveAndGoToScheduleButton()
                .clickCreateTriggerRadioButton()
                .clickExecutionDateInput()
                .chooseTheNextDay(Methods.getNextDayOfMonth())
                .setExecutionTimeInput(LocalTime.NOON.toString());

        Assert.assertTrue(createJobModalWindow.checkModalWindowHeader("Create new job based on\n" +
                workflowName + "\n" + workflowOrTestPlan), "The modal window has incorrect header");

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                Main.report.logInfo("'Daily' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnDaily()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth()
                        .clickCreateTriggerButton();

                triggerID = createJobModalWindow.getCreatedJobID();
                Assert.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan +" everyday at " +
                                LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + "."),
                        "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue() + "\ninstead of\n" +
                                "Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan +" everyday at " +
                                LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + ".");

                break;
            case "weekly":
                Main.report.logInfo("'Weekly' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnWeekly()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth()
                        .clickCreateTriggerButton();

                triggerID = createJobModalWindow.getCreatedJobID();
                Assert.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan +" every " + Methods.getNameOfNextDay() + " at " +
                                LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + "."),
                        "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue() + "\ninstead of\n" +
                                "Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan +" every " + Methods.getNameOfNextDay() + " at " +
                                LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + ".");


                break;
            case "monthly":
                Main.report.logInfo("'Monthly' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnMonthly()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth()
                        .clickCreateTriggerButton();

                triggerID = createJobModalWindow.getCreatedJobID();
                Assert.assertTrue(createJobModalWindow.checkPopUpValue(("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan + " " + Methods.getOrdinalIndicatorOfNextDay() +
                                " of every month at " + LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + ".")),
                        "The popup after creating the trigger has incorrect text:\n" + createJobModalWindow.getPopUpValue() + "\ninstead of \n" + "Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan + " " + Methods.getOrdinalIndicatorOfNextDay() +
                                " of every month at " + LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + ".");

                break;
            case "never":
                Main.report.logInfo("Option 'Never' of execution frequency has been selected");
                createJobModalWindow.clickCreateTriggerButton();
                triggerID = createJobModalWindow.getCreatedJobID();
                Assert.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan +" at " + Methods.getDateOfNextDay("dd/MM/YYYY") +
                                " " + LocalTime.NOON.toString() + "."),
                        "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue() + "\ninstead of\n" + "Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " " + workflowOrTestPlan +" at " + Methods.getDateOfNextDay("dd/MM/YYYY") +
                                " " + LocalTime.NOON.toString() + ".");

                break;
            default:
                Assert.fail("There is a wrong value of 'executionFrequency' parameter");
        }

        Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());

        createJobModalWindow.clickGoToTriggerDetailsButton();
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
        Main.report.logPass("*********Job was created");
        Main.report.logInfo("*********Start test");
        driver.get(pfSignInUrl);
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
                trgDetailsInScheduleTable = "Every " + Methods.getNameOfNextDay() + " at " + LocalTime.NOON.toString();
                Assert.assertEquals(displayedTriggers.getTriggerDetails(),trgDetailsInScheduleTable,
                        "The 'Trigger details' in the Schedule table displays the wrong date: " + displayedTriggers.getTriggerDetails());
                Main.report.logPass("The 'Trigger details' in the Schedule table displays the correct date: " + trgDetailsInScheduleTable);
                break;
            case "monthly":
                trgDetailsInScheduleTable = Methods.getOrdinalIndicatorOfNextDay() + " of every month at " + LocalTime.NOON.toString();
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
        obj.put("workflowName", workflowName);
        obj.put("executionFrequency", executionFrequency);
        obj.put("triggerDetails", triggerDetails);
        obj.put("nextRun", nextRun);
        System.setProperty("output", obj.toString());
    }
}
