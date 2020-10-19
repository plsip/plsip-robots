package ai.makeitright.tests.tasks.deletetask;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.DeleteTaskModalWindow;
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
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DeleteTaskTest extends DriverConfig {

    //from configuration
    private String pfCompanyName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String taskname;
    private String taskName;

    @Before
    public void before() {
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        taskname = System.getProperty("previousResult.taskname");
        taskName = System.getProperty("previousResult.taskName");
    }

    @Test
    public void deleteTask() {
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfCompanyName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Tasks");

        TasksPage tasksPage = new TasksPage(driver);
        tasksPage.filterTask(taskName);

        tasksPage.checkIfOneRowDisplayed();

        DisplayedTasks displayedTasks = tasksPage.getTasksTable().getTasksFirstRowData();
        Assertions.assertNotNull(displayedTasks,"There is no task with name: '" + taskName + "'");
        Assertions.assertEquals(taskName,displayedTasks.getName(),"The name of filtered task is not right");
        Main.report.logPass("Task with name " + taskName + " was found");

        TaskDetailsPage taskDetailsPage = tasksPage.clickTaskNameLink(displayedTasks.getLnkName(), taskName);
        Assertions.assertEquals(taskDetailsPage.getName(),taskName,"Name of task on details page is not right");

        DeleteTaskModalWindow deleteTaskModalWindow = taskDetailsPage.clickDeleteTaskButton();
        Assertions.assertTrue(deleteTaskModalWindow.checkTaskNameToDelete(taskName));
        Main.report.logPass("Task name on modal window is right");

        tasksPage = deleteTaskModalWindow.clickDeleteTask();
        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assertions.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"),"Banner ribbon on popup window is not dark green");
        Assertions.assertTrue(statusPopupWindow.isAlertStatus("Yesss!"),"There is no right status on popup window");
        Assertions.assertTrue(statusPopupWindow.isAlertMessage2("The game's over for the task " + taskName + "\n" +
                "You can move on \uD83C\uDFC3\u200D♂️"),"There is no right alert message");

        displayedTasks = tasksPage.getTasksTable().getTasksRowData(taskName);
        Assertions.assertNull(displayedTasks, "In the task table is task with name '" + taskName + "'");

        tasksPage.filterTask(taskName);
        tasksPage.checkIfNoneRowDisplayed();
        Main.report.logPass("In the tasks table there is no task after search with name '" + taskName + "'");
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskName", taskName);
        obj.put("taskname",taskname + " || Delete task");
        System.setProperty("output", obj.toString());
        driver.close();
    }


}
