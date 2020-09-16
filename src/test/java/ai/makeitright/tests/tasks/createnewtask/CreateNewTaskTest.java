package ai.makeitright.tests.tasks.createnewtask;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.CreateTaskModalWindow;
import ai.makeitright.pages.tasks.TaskDetailsPage;
import ai.makeitright.pages.tasks.TasksPage;
import ai.makeitright.utilities.DriverConfig;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CreateNewTaskTest extends DriverConfig {

    //from configuration
    private String createdBy;
    private String email;
    private String password;
    private String pfCompanyName;
    private String powerFarmUrl;
    private String repository;
    private String scriptDirectory;
    private String taskName;
    private String technology;

    @Before
    public void before() {
        createdBy = System.getProperty("inputParameters.createdBy");
        email = System.getProperty("inputParameters.pfUserEmail");
        password = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        powerFarmUrl = System.getProperty("inputParameters.pfSignInUrl");
        repository = System.getProperty("inputParameters.repository");
        scriptDirectory = System.getProperty("inputParameters.scriptDirectory");
        taskName = System.getProperty("inputParameters.taskName");
        technology = System.getProperty("inputParameters.technology");
    }

    @Test
    public void createNewTask() {

        driver.get(powerFarmUrl);
        driver.manage().window().maximize();
        LoginPage loginPage = new LoginPage(driver, powerFarmUrl, pfCompanyName);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Tasks");

        TasksPage tasksPage = new TasksPage(driver);
        CreateTaskModalWindow createTaskModalWindow = tasksPage.clickCreateNewTaskButton();
        createTaskModalWindow.setName(taskName);
        createTaskModalWindow.selectTechnology(technology);
        createTaskModalWindow.selectScriptDirectory(repository);
        createTaskModalWindow.setScriptDirectory(scriptDirectory);
        TaskDetailsPage taskDetailsPage = createTaskModalWindow.clickCreateTaskButton();

        Assertions.assertTrue(taskDetailsPage.checkListOfCommitsIsDisplayed(),"The list of commits wasn't loaded");

        taskName = createTaskModalWindow.getName();
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskName", taskName);
        obj.put("createdBy", createdBy);
        obj.put("technology",technology);
        obj.put("scriptDirectory",repository + "tree/master/" + scriptDirectory);
        obj.put("taskname","Create new task");
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
