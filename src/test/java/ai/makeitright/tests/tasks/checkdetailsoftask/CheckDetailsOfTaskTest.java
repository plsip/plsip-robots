package ai.makeitright.tests.tasks.checkdetailsoftask;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.DisplayedTasks;
import ai.makeitright.pages.tasks.TaskDetailsPage;
import ai.makeitright.pages.tasks.TasksPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class CheckDetailsOfTaskTest extends DriverConfig {

    private String createdBy;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String scriptDirectory;
    private String taskname;
    private String taskName;
    private String technology;

    @Before
    public void before() {
        createdBy = System.getProperty("previousResult.createdBy");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        scriptDirectory = System.getProperty("previousResult.scriptDirectory");
        taskname = System.getProperty("previousResult.taskname");
        taskName = System.getProperty("previousResult.taskName");
        technology = System.getProperty("previousResult.technology");
    }

    @Test
    public void checkDetailsOfTask() {
        driver.get(pfSignInUrl);
        driver.manage().window().maximize();
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Tasks");

        TasksPage tasksPage = new TasksPage(driver);
        tasksPage.filterTask(taskName);

        DisplayedTasks displayedTasks = tasksPage.getTasksTable().getTasksFirstRowData();
        Assertions.assertNotNull(displayedTasks,"There is no task with name: '" + taskName + "'");
        Assertions.assertEquals(taskName,displayedTasks.getName(),"The name of task is not right");
        Main.report.logPass("Task has right value for 'Name'");
        Assertions.assertEquals(createdBy,displayedTasks.getCreatedBy(),"The value 'Created by' for task " + taskName + " is not like expected");
        Main.report.logPass("Task has right value for 'Created by'");
        Assertions.assertEquals(technology,displayedTasks.getTechnology(),"The value 'Technology' for task " + taskName + " is not like expected");
        Main.report.logPass("Task has right value for 'Technology'");

        TaskDetailsPage taskDetailsPage = tasksPage.clickTaskNameLink(displayedTasks.getLnkName(), taskName);
        Assertions.assertEquals(taskDetailsPage.getName(),taskName,"Name of task on details page is not right");
        Main.report.logPass("Task has right name");
        Assertions.assertEquals(taskDetailsPage.getCreatedBy(),createdBy,"The value for 'CREATED BY' on Details page is not right");
        Main.report.logPass("Task has right value for 'CREATED BY'");
        Assertions.assertEquals(taskDetailsPage.getTechnology(),technology,"The value for 'TECHNOLOGY' on Details page is not right");
        Main.report.logPass("Task has right value for 'TECHNOLOGY'");
        Assertions.assertEquals(taskDetailsPage.getAssignedFolderInRepository(),scriptDirectory,"The value for 'ASSIGNED FOLDER IN REPOSITORY' on Details page is not right");
        Main.report.logPass("Task has right value for 'ASSIGNED FOLDER IN REPOSITORY'");
        Assertions.assertTrue(taskDetailsPage.checkListOfCommitsIsDisplayed(),"The list of commits wasn't loaded");

    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskName", taskName);
        obj.put("taskname",taskname + "\nCheck details of task");
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
