package ai.makeitright.tests.tasks.singlecheckdetailsoftask;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.*;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CheckDetailsOfTask32Test extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Tests/Tasks - Check details of test/task [P20Ct-32]";
        taskName = System.getProperty("inputParameters.beginningOfTaskName");
        technology = System.getProperty("inputParameters.technology");
    }

    @Test
    public void checkDetailsOfTask() throws InterruptedException {
        Main.report.logInfo("******************************\nBefore test - create new task/test:\n");
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
        createTaskModalWindow
                .setName(taskName)
                .selectTechnology(technology)
                .selectScriptDirectory(repository)
                .setScriptDirectory(scriptDirectory);
        if (pfGlossary.equals("TA")) {
            createTaskModalWindow.clickCreateTestButton();
        } else {
            createTaskModalWindow.clickCreateTaskButton();
        }

        taskName = createTaskModalWindow.getName();


        Main.report.logPass("******************************\nStart test");
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Tests");
            tasksPage = new TasksPage(driver, pfGlossary);
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Tasks");
            tasksPage = new TasksPage(driver, pfGlossary);
        }

        tasksPage.filterOneTask(taskName);

        DisplayedTasks displayedTasks = tasksPage.getTasksTable().getTasksFirstRowData();
        Assert.assertNotNull(displayedTasks,"There is no task with name: '" + taskName + "'");
        Assert.assertEquals(displayedTasks.getName(),taskName,"The name of task is not right");
        Main.report.logPass("Task has right value for 'Name'");
        Assert.assertEquals(displayedTasks.getCreatedBy(),new TopPanel(driver).getCreatedBy(),"The value 'Created by' for task " + taskName + " is not like expected");
        Main.report.logPass("Task has right value for 'Created by'");
        Assert.assertEquals(displayedTasks.getTechnology(),technology,"The value 'Technology' for task " + taskName + " is not like expected");
        Main.report.logPass("Task has right value for 'Technology'");

        TaskDetailsPage taskDetailsPage = tasksPage.clickTaskNameLink(displayedTasks.getLnkName(), taskName);
        Assert.assertEquals(taskDetailsPage.getName(),taskName,"Name of task on details page is not right");
        Main.report.logPass("Task has right name");
        Assert.assertEquals(taskDetailsPage.getCreatedBy(),new TopPanel(driver).getCreatedBy(),"The value for 'CREATED BY' on Details page is not right");
        Main.report.logPass("Task has right value for 'CREATED BY'");
        Assert.assertEquals(taskDetailsPage.getTechnology(),technology,"The value for 'TECHNOLOGY' on Details page is not right");
        Main.report.logPass("Task has right value for 'TECHNOLOGY'");
        Assert.assertEquals(taskDetailsPage.getAssignedFolderInRepository(),repository + "tree/master/" + scriptDirectory,"The value for 'ASSIGNED FOLDER IN REPOSITORY' on Details page is not right");
        Main.report.logPass("Task has right value for 'ASSIGNED FOLDER IN REPOSITORY'");
        Assert.assertTrue(taskDetailsPage.checkListOfCommitsIsDisplayed(),"The list of commits wasn't loaded");

        Main.report.logPass("**********Test has been completed successfully!");
        Main.report.logInfo("**********Now delete task");
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

        displayedTasks = tasksPage.getTasksTable().getTasksFirstRowData();
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
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskTestName", taskName);
        System.setProperty("output", obj.toString());
//        TopPanel topPanel = new TopPanel(driver);
//        topPanel.clickTopPanelButton()
//                .clickLogOutLink();
    }

}
