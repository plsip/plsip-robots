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
                .clickRadioBtnToScheduleJob()
                .clickExecutionDateInput()
                .chooseTheNextDay(Methods.getNextDayOfMonth())
                .setExecutionTimeInput(Methods.getCurrentTime());

        switch (executionFrequency.toLowerCase()) {
            case "daily":
                Main.report.logInfo("'Daily' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnDaily()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth();
                break;
            case "weekly":
                Main.report.logInfo("'Weekly' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnWeekly()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth();
                break;
            case "monthly":
                Main.report.logInfo("'Monthly' execution frequency has been selected");
                createJobModalWindow
                        .clickRadioBtnMonthly()
                        .clickFinishDateInput()
                        .chooseFirstDayOfNextMonth();
                break;
            default:
                Main.report.logInfo("No execution frequency has been selected");
                break;
        }

        createJobModalWindow.clickCreateTriggerButton();

        triggerID = createJobModalWindow.getCreatedJobID();
        Assertions.assertTrue(createJobModalWindow.getPopUpValue().contains("Your trigger (ID: " + triggerID + ") was successfully created!"),
                "The popup has the wrong text: ");
        Main.report.logPass("The popup after creating the trigger has the correct text: " + createJobModalWindow.getPopUpValue());

        TriggerDetailsPage triggerDetailsPage = createJobModalWindow.clickGoToTriggerDetailsButton();
        Assertions.assertTrue(triggerDetailsPage.checkTriggerID(triggerID), "The value of JOB ID is incorrect.");
        Main.report.logPass("In the trigger details there is a correct trigger ID value displayed: " + triggerID);

        Assertions.assertTrue(triggerDetailsPage.checkTriggerHeader(workflowName),
                "The trigger header is not correct.");
        Main.report.logPass("In the trigger details there is a correct trigger header displayed: " + triggerDetailsPage.getTriggerHeader());
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", "Create a new job with a trigger");
        obj.put("workflowName", workflowName);
        obj.put("triggerID", triggerID);
        System.setProperty("output", obj.toString());
        driver.close();
    }
}



