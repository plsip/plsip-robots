package ai.makeitright.tests.jobs.singledeletetrigger;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedules.SchedulePage;
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

public class DeleteTrigger280Test extends DriverConfig {

    private String argumentsCollection;
    private String executionFrequency;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
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
        Main.taskname = pfGlossary + ": TC - Schedule - Delete created trigger (unfinished scheduled job) [P20Ct-280]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        workflowName = System.getProperty("inputParameters.workflowOrTestPlanName");
    }

    @Test
    public void deleteTrigger() {
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
            WorkflowsPage workflowsPage = new WorkflowsPage(driver,pfGlossary);
            createJobModalWindow = workflowsPage.clickCreateJobButton(workflowName);
            workflowOrTestPlan = "workflow";
        }

        Assert.assertNotNull(createJobModalWindow,"Modal window for creating job was not open");
        createJobModalWindow
                .clickSaveAndGoToArgumentsCollectionsAndTagsButton()
                .chooseGlobalArgumentsCollection(argumentsCollection)
                .clickSaveAndGoToArgumentsValuesButton();
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


        Main.report.logPass("*********Job was created");
        Main.report.logInfo("*********Start test");
        driver.get(pfSignInUrl);
        leftMenu.openPageBy("Schedule");

        SchedulePage schedulePage = new SchedulePage(driver);
        schedulePage
                .filterTrigger(triggerID)
                .clickPauseTriggerButton(triggerID)
                .clickDeleteTriggerButton(triggerID)
                .confirmDeletionOfTrigger();

        Assert.assertFalse(schedulePage.checkIfScheduleTableIsDisplayed());
        Main.report.logPass("The trigger is no longer on the trigger list");
        Main.report.logPass("**********Test has been completed successfully!");
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("triggerID", triggerID);
        obj.put("workflowName", workflowName);
        System.setProperty("output", obj.toString());
    }
}
