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
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteTaskTest extends DriverConfig {

    //from configuration
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String taskname;
    private String taskName;

    @BeforeTest
    public void before() {
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        taskname = System.getProperty("previousResult.taskname");
        taskName = System.getProperty("previousResult.taskName");
    }

    @Test
    public void deleteTask() {
        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        switch(pfGlossary) {
            case "TA":
                leftMenu.openPageBy("Tests");
                break;
            case "RPA":
                leftMenu.openPageBy("Tasks");
                break;
        }

        TasksPage tasksPage = new TasksPage(driver);
        tasksPage.filterTask(taskName);

        tasksPage.checkIfOneRowDisplayed();

        DisplayedTasks displayedTasks = tasksPage.getTasksTable().getTasksFirstRowData();
        Assert.assertNotNull(displayedTasks,"There is no task with name: '" + taskName + "'");
        Assert.assertEquals(displayedTasks.getName(),taskName,"The name of filtered task is not right");
        Main.report.logPass("Task with name " + taskName + " was found");

        TaskDetailsPage taskDetailsPage = tasksPage.clickTaskNameLink(displayedTasks.getLnkName(), taskName);
        Assert.assertEquals(taskDetailsPage.getName(),taskName,"Name of task on details page is not right");

        DeleteTaskModalWindow deleteTaskModalWindow = taskDetailsPage.clickDeleteTaskButton();
        Assert.assertTrue(deleteTaskModalWindow.checkTaskNameToDelete(taskName));
        Main.report.logPass("Task name on modal window is right");

        tasksPage = deleteTaskModalWindow.clickDeleteTask();
        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"),"Banner ribbon on popup window is not dark green");
        Assert.assertTrue(statusPopupWindow.isAlertStatus("Yesss!"),"There is no right status on popup window");
        Assert.assertTrue(statusPopupWindow.isAlertMessage2("The game's over for the task " + taskName + "\n" +
                "You can move on \uD83C\uDFC3\u200D♂️"),"There is no right alert message");

        displayedTasks = tasksPage.getTasksTable().getTasksRowData(taskName);
        Assert.assertNull(displayedTasks, "In the task table is task with name '" + taskName + "'");

        tasksPage.filterTask(taskName);
        tasksPage.checkIfNoneRowDisplayed();
        Main.report.logPass("In the tasks table there is no task after search with name '" + taskName + "'");
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskName", taskName);
        obj.put("taskname",taskname + " || Delete task");
        System.setProperty("output", obj.toString());
    }


}
