package ai.makeitright.tests.tasks.singledeletetask;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.*;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteTask190Test extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Tasks/Tests - Delete task [P20Ct-190]";
        taskName = System.getProperty("inputParameters.beginningOfTaskName");
        technology = System.getProperty("inputParameters.technology");
    }

    @Test
    public void deleteTask() throws InterruptedException {
        Main.report.logInfo("********Before test - create new task");
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
        Main.report.logPass("*********Task was created");
        Main.report.logInfo("*********Start test");
        driver.get(pfSignInUrl);

        String taskOrTest;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Tests");
            tasksPage = new TasksPage(driver, pfGlossary);
            taskOrTest = "test";
        } else {
            leftMenu.openPageBy("Tasks");
            tasksPage = new TasksPage(driver, pfGlossary);
            taskOrTest = "task";
        }

        tasksPage.filterTask(taskName);

        tasksPage.checkIfOneRowDisplayed();

        DisplayedTasks displayedTasks = tasksPage.getTasksTable().getTasksFirstRowData();
        Assert.assertNotNull(displayedTasks,"There is no task with name: '" + taskName + "'");
        Assert.assertEquals(displayedTasks.getName(),taskName,"The name of filtered task is not right");
        Main.report.logPass("Task with name " + taskName + " was found");

        taskDetailsPage = tasksPage.clickTaskNameLink(displayedTasks.getLnkName(), taskName);
        Assert.assertEquals(taskDetailsPage.getName(),taskName,"Name of task on details page is not right");

        DeleteTaskModalWindow deleteTaskModalWindow = taskDetailsPage.clickDeleteButton(pfGlossary);

        Assert.assertTrue(deleteTaskModalWindow.checkTaskNameToDelete(taskName, pfGlossary));
        Main.report.logPass("Task name on modal window is right");

        tasksPage = deleteTaskModalWindow.clickDeleteTask(pfGlossary);
        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"),"Banner ribbon on popup window is not dark green");
        Assert.assertTrue(statusPopupWindow.isAlertStatus("Yesss!"),"There is no right status on popup window");
        Assert.assertTrue(statusPopupWindow.isAlertMessage2("The game's over for the " + taskOrTest + " " + taskName + "\n" +
                "You can move on \uD83C\uDFC3\u200D♂️"),"There is no right alert message");

        displayedTasks = tasksPage.getTasksTable().getTasksRowData(taskName);
        Assert.assertNull(displayedTasks, "In the table is task/test with name '" + taskName + "'");

        tasksPage.filterTask(taskName);
        tasksPage.checkIfNoneRowDisplayed();
        Main.report.logPass("In the tasks table there is no task/test after search with name '" + taskName + "'");
        Main.report.logPass("**********Test has been completed successfully!");
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskName", taskName);
        System.setProperty("output", obj.toString());
    }
}
