package ai.makeitright.tests.jobs.createnewtriggerjob;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.schedules.TriggerDetailsPage;
import ai.makeitright.pages.workflows.CreateJobModalWindow;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalTime;

public class CreateNewTriggerJobTest extends DriverConfig {

    //from configuration
    private String email;
    private String password;
    private String pfOrganizationCardName;
    private String powerFarmUrl;
    private String workflowName;
    private String argumentsCollection;
    private String executionFrequency;

    //for reporting:
    private String triggerID;
    private String triggerDetails;
    private String nextRun;
    private String finishDate;

    @Before
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
        executionFrequency = System.getProperty("inputParameters.executionFrequency");
    }

    @Test
    public void createNewJobWithTrigger() {
        driver.get(powerFarmUrl);

        LoginPage loginPage = new LoginPage(driver, powerFarmUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);

        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Workflows");

        WorkflowsPage workflowsPage = new WorkflowsPage(driver);

        CreateJobModalWindow createJobModalWindow = workflowsPage.clickCreateJobButton(workflowName);
        createJobModalWindow
                .clickSaveAndGoToCollectionButton()
                .chooseGlobalArgumentsCollection(argumentsCollection)
                .clickSaveAndGoToValuesButton();
        Assertions.assertTrue(createJobModalWindow.checkIfCorrectCollectionIsDisplayed(argumentsCollection),
                "An incorrect collection was selected.");
        Main.report.logPass("The correct collection was chosen: " + argumentsCollection);

        createJobModalWindow
                .clickSaveAndGoToScheduleButton()
                .clickCreateTriggerRadioButton()
                .clickExecutionDateInput()
                .chooseTheNextDay(Methods.getNextDayOfMonth())
                .setExecutionTimeInput(LocalTime.NOON.toString());

        Assertions.assertTrue(createJobModalWindow.checkModalWindowHeader("Create new job based on\n" +
                workflowName + "\n" + "workflow"), "The modal window has incorrect header");

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                Main.report.logInfo("'Daily' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnDaily()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth()
                        .clickCreateTriggerButton();

                triggerID = createJobModalWindow.getCreatedJobID();
                Assertions.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
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
                Assertions.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
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
                Assertions.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " workflow " + Methods.getOrdinalIndicatorOfNextDay() +
                                " of every month at " + LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth() + "."),
                                "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue());
                Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());
                break;
            case "never":
                Main.report.logInfo("Option 'Never' of execution frequency has been selected");
                createJobModalWindow.clickCreateTriggerButton();
                triggerID = createJobModalWindow.getCreatedJobID();
                Assertions.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " workflow at " + Methods.getDateOfNextDay("dd/MM/YYYY") +
                                " " + LocalTime.NOON.toString() + "."),
                                "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue());
                Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());
                break;
            default:
                Assertions.fail("There is a wrong value of 'executionFrequency' parameter");
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

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", "Create a new job with a trigger");
        obj.put("workflowName", workflowName);
        obj.put("triggerID", triggerID);
        obj.put("executionFrequency", executionFrequency);
        obj.put("triggerDetails", triggerDetails);
        obj.put("nextRun", nextRun);
        obj.put("finishDate", finishDate);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}



