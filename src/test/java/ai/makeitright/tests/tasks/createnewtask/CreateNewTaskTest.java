package ai.makeitright.tests.tasks.createnewtask;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settigns.RepositoryPage;
import ai.makeitright.pages.tasks.CreateTaskModalWindow;
import ai.makeitright.pages.tasks.TasksPage;
import ai.makeitright.utilities.DriverConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CreateNewTaskTest extends DriverConfig {

    //from configuration
    private String companyname;
    private String email;
    private String password;
    private String powerFarmUrl;
    private String taskname;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("inputParameters.companyname","mir-mvp");
        System.setProperty("inputParameters.email","katarzyna.raczkowska@makeitright.ai");
        System.setProperty("secretParameters.password","TestyAutomatyczne");
        System.setProperty("inputParameters.powerFarm_url","https://development.powerfarm.ai/signin");
        System.setProperty("inputParameters.taskname","notuse_automated_task");
    }

    @Before
    public void before() {
        companyname = System.getProperty("inputParameters.companyname");
        email = System.getProperty("inputParameters.email");
        password = System.getProperty("secretParameters.password");
        powerFarmUrl = System.getProperty("inputParameters.powerFarm_url");
        taskname = System.getProperty("inputParameters.taskname");
    }

    @Test
    public void createNewTask() {

        driver.get(powerFarmUrl);
        driver.manage().window().maximize();
        LoginPage loginPage = new LoginPage(driver, powerFarmUrl);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Tasks");

        TasksPage tasksPage = new TasksPage(driver);
        CreateTaskModalWindow createTaskModalWindow = tasksPage.clickCreateNewTaskButton();
        createTaskModalWindow.setName(taskname);

    }
}
