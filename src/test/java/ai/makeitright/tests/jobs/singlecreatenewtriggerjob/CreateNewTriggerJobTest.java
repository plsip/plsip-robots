package ai.makeitright.tests.jobs.singlecreatenewtriggerjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
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

public class CreateNewTriggerJobTest extends DriverConfig {

    //from configuration
    private String argumentsCollection;
    private String channel;
    private String executionFrequency;
    private String hookUrl;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String workflowName;

    //for reporting:
    private String finishDate;
    private String nextRun;
    private String triggerDetails;
    private String triggerID;

    @BeforeTest
    public void before() {
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
        channel = System.getProperty("inputParameters.channel");
        Main.channel = this.channel;
        executionFrequency = System.getProperty("inputParameters.executionFrequency");
        hookUrl = System.getProperty("secretParameters.hookUrl");
        Main.hookUrl = this.hookUrl;
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.taskname = pfGlossary + ": TC - Jobs - Create job with trigger [P20Ct-85]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        workflowName = System.getProperty("inputParameters.workflowOrTestPlanName");
    }

    @Test
    public void createNewJobWithTrigger() {
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        CreateJobModalWindow createJobModalWindow = null;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
            TestPlansPage testPlandPage = new TestPlansPage(driver);
            createJobModalWindow = testPlandPage.clickCreateJobButton(workflowName);
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Workflows");
            WorkflowsPage workflowsPage = new WorkflowsPage(driver);
            createJobModalWindow = workflowsPage.clickCreateJobButton(workflowName);
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
        if (pfGlossary.equals("TA")) {
            Assert.assertTrue(createJobModalWindow.checkModalWindowHeader("Create new job based on\n" +
                    workflowName + "\n" + "test plan"), "The modal window has incorrect header");
        } else if (pfGlossary.equals("RPA")) {
            Assert.assertTrue(createJobModalWindow.checkModalWindowHeader("Create new job based on\n" +
                    workflowName + "\n" + "workflow"), "The modal window has incorrect header");
        }
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
                                "It will create job with " + workflowName + " workflow everyday at " +
                                LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + "."),
                        "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue());
                Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());
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
                                "It will create job with " + workflowName + " workflow every " + Methods.getNameOfNextDay() + " at " +
                                LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + "."),
                        "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue());
                Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());
                break;
            case "monthly":
                Main.report.logInfo("'Monthly' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnMonthly()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth()
                        .clickCreateTriggerButton();

                triggerID = createJobModalWindow.getCreatedJobID();
                Assert.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " workflow " + Methods.getOrdinalIndicatorOfNextDay() +
                                " of every month at " + LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + "."),
                        "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue());
                Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());
                break;
            case "never":
                Main.report.logInfo("Option 'Never' of execution frequency has been selected");
                createJobModalWindow.clickCreateTriggerButton();
                triggerID = createJobModalWindow.getCreatedJobID();
                Assert.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " workflow at " + Methods.getDateOfNextDay("dd/MM/YYYY") +
                                " " + LocalTime.NOON.toString() + "."),
                        "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue());
                Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());
                break;
            default:
                Assert.fail("There is a wrong value of 'executionFrequency' parameter");
        }

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
