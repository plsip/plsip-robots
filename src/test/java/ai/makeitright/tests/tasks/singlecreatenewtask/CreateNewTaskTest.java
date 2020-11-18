package ai.makeitright.tests.tasks.singlecreatenewtask;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.CreateTaskModalWindow;
import ai.makeitright.pages.tasks.TaskDetailsPage;
import ai.makeitright.pages.tasks.TasksPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateNewTaskTest extends DriverConfig {

    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String repository;
    private String scriptDirectory;
    private String taskName;
    private String technology;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        repository = System.getProperty("inputParameters.repository");
        scriptDirectory = System.getProperty("inputParameters.scriptDirectory");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        Main.taskname = pfGlossary + ": TC - Tasks/Tests - Create new task/test [P20Ct-31]";
        taskName = System.getProperty("inputParameters.beginningOfTaskName");
        technology = System.getProperty("inputParameters.technology");
    }

    @Test
    public void createNewTask() {

        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        TasksPage tasksPage;
        CreateTaskModalWindow createTaskModalWindow;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Tests");
            tasksPage = new TasksPage(driver, pfGlossary);
            createTaskModalWindow = tasksPage.clickCreateNewTestButton();
        } else {
            leftMenu.openPageBy("Tasks");
            tasksPage = new TasksPage(driver, pfGlossary);
            createTaskModalWindow = tasksPage.clickCreateNewTaskButton();
        }


        Assert.assertNotNull(createTaskModalWindow);
        createTaskModalWindow
                .setName(taskName)
                .selectTechnology(technology)
                .selectScriptDirectory(repository)
                .setScriptDirectory(scriptDirectory);
        TaskDetailsPage taskDetailsPage;
        if (pfGlossary.equals("TA")) {
            taskDetailsPage = createTaskModalWindow.clickCreateTestButton();
        } else {
            taskDetailsPage = createTaskModalWindow.clickCreateTaskButton();
        }

        Assert.assertTrue(taskDetailsPage.checkListOfCommitsIsDisplayed(),"The list of commits wasn't loaded");

        taskName = createTaskModalWindow.getName();
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskTestName", taskName);
        obj.put("technology",technology);
        obj.put("scriptDirectory",repository + "tree/master/" + scriptDirectory);
        System.setProperty("output", obj.toString());
    }
}
