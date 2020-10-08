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
    private String pfCompanyName;
    private String powerFarmUrl;
    private String workflowName;
    private String argumentsCollection;
    private String executionFrequency;

    //for reporting:
    private String triggerID;
    private String triggerDetails;
    private String nextRun;

    @Before
    public void before() {
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        workflowName = System.getProperty("inputParameters.workflowName");
        argumentsCollection = System.getProperty("inputParameters.argumentsCollection");
        executionFrequency = System.getProperty("inputParameters.executionFrequency");
    }

    @Test
    public void createNewJobWithTrigger() {
        driver.get(powerFarmUrl);

        LoginPage loginPage = new LoginPage(driver, powerFarmUrl, pfCompanyName);
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
                Main.report.logInfo("No execution frequency has been selected, default 'Never' option is marked");
                createJobModalWindow.clickCreateTriggerButton();
                triggerID = createJobModalWindow.getCreatedJobID();
                Assertions.assertTrue(createJobModalWindow.checkPopUpValue("Your trigger (ID: " + triggerID + ") was successfully created!\n" +
                                "It will create job with " + workflowName + " workflow at " + Methods.getDateOfNextDay("dd/MM/YYYY") +
                                " " + LocalTime.NOON.toString() + "."),
                                "The popup after creating the trigger has incorrect text: " + createJobModalWindow.getPopUpValue());
                Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());
                break;
        }

        TriggerDetailsPage triggerDetailsPage = createJobModalWindow.clickGoToTriggerDetailsButton();
        Assertions.assertTrue(triggerDetailsPage.checkTriggerID(triggerID),
                "The value of TRIGGER ID is incorrect: " + triggerID);
        Main.report.logPass("In the trigger details there is a correct trigger ID value displayed: " + triggerID);

        Assertions.assertTrue(triggerDetailsPage.checkTriggerHeader(workflowName),
                "The trigger header is not correct: " + triggerDetailsPage.getTriggerHeader());
        Main.report.logPass("In the trigger details there is a correct trigger header displayed: " + triggerDetailsPage.getTriggerHeader());

        Assertions.assertTrue(triggerDetailsPage.checkCreatedBy(),
                "Value for 'CREATED BY' in section 'Information' should be the same as on the top of page");
        Main.report.logPass("Value for 'CREATED BY' in section 'Information' is the same as in top panel: " + triggerDetailsPage.getCreatedBy());

        Assertions.assertTrue(triggerDetailsPage.checkButtonPauseTriggerIsEnabled(),
                "Button 'Pause trigger' should be visible and enabled");
        Main.report.logPass("Button 'Pause trigger' is visible and enabled");

        nextRun = Methods.getDateOfNextDay("dd/MM/YYYY") + " " + LocalTime.NOON.toString();
        Assertions.assertTrue(triggerDetailsPage.checkNextRun(nextRun),
                "The 'Next run' section displays the wrong date: " + triggerDetailsPage.getNextRun());
        Main.report.logPass("The 'NEXT RUN' section displays the correct date: " + triggerDetailsPage.getNextRun());

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                triggerDetails = "Everyday at " +
                        LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth();
                Assertions.assertTrue(triggerDetailsPage.checkTriggerDetails(triggerDetails),
                        "The trigger details display the wrong date: " + triggerDetailsPage.getTriggerDetails());
                Main.report.logPass("The trigger details display the correct date: " + triggerDetailsPage.getTriggerDetails());

                Assertions.assertTrue(triggerDetailsPage.checkFinishDate(Methods.getFirstDayOfNextMonth() + " 23:59"),
                        "The 'FINISH DATE' section displays the wrong date: " + triggerDetailsPage.getFinishDate());
                Main.report.logPass("The 'FINISH DATE' section displays the correct date: " + triggerDetailsPage.getFinishDate());
                break;
            case "weekly":
                triggerDetails = "Every " + Methods.getNameOfNextDay() + " at " +
                        LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth();
                Assertions.assertTrue(triggerDetailsPage.checkTriggerDetails(triggerDetails),
                        "The trigger details display the wrong date: " + triggerDetailsPage.getTriggerDetails());
                Main.report.logPass("The trigger details display the correct date: " + triggerDetailsPage.getTriggerDetails());

                Assertions.assertTrue(triggerDetailsPage.checkFinishDate(Methods.getFirstDayOfNextMonth() + " 23:59"),
                        "The 'FINISH DATE' section displays the wrong date: " + triggerDetailsPage.getFinishDate());
                Main.report.logPass("The 'FINISH DATE' section displays the correct date: " + triggerDetailsPage.getFinishDate());
                break;
            case "monthly":
                triggerDetails = Methods.getOrdinalIndicatorOfNextDay() +
                        " of every month at " + LocalTime.NOON.toString() + " till " + Methods.getFirstDayOfNextMonth();
                Assertions.assertTrue(triggerDetailsPage.checkTriggerDetails(triggerDetails),
                        "The trigger details display the wrong date: " + triggerDetailsPage.getTriggerDetails());
                Main.report.logPass("The trigger details display the correct date: " + triggerDetailsPage.getTriggerDetails());

                Assertions.assertTrue(triggerDetailsPage.checkFinishDate(Methods.getFirstDayOfNextMonth() + " 23:59"),
                        "The 'FINISH DATE' section displays the wrong date: " + triggerDetailsPage.getFinishDate());
                Main.report.logPass("The 'FINISH DATE' section displays the correct date: " + triggerDetailsPage.getFinishDate());
                break;
            case "never":
                triggerDetails = "At "+ Methods.getDateOfNextDay("dd/MM/YYYY") + " " + LocalTime.NOON.toString();
                Assertions.assertTrue(triggerDetailsPage.checkTriggerDetails(triggerDetails),
                        "The trigger details display the wrong date: " + triggerDetailsPage.getTriggerDetails());
                Main.report.logPass("The trigger details display the correct date: " + triggerDetailsPage.getTriggerDetails());
                break;
            default:
                Main.report.logInfo("No execution frequency has been selected, default 'Never' option is marked");
                triggerDetails = "At "+ Methods.getDateOfNextDay("dd/MM/YYYY") + " " + LocalTime.NOON.toString();
                Assertions.assertTrue(triggerDetailsPage.checkTriggerDetails(triggerDetails),
                        "The trigger details display the wrong date: " + triggerDetailsPage.getTriggerDetails());
                Main.report.logPass("The trigger details display the correct date: " + triggerDetailsPage.getTriggerDetails());
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
        System.setProperty("output", obj.toString());
        driver.close();
    }
}



