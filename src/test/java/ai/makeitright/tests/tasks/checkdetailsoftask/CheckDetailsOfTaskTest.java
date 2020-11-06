package ai.makeitright.tests.tasks.checkdetailsoftask;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
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

public class CheckDetailsOfTaskTest extends DriverConfig {

    private String createdBy;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String scriptDirectory;
    private String taskname;
    private String taskName;
    private String technology;

    @BeforeTest
    public void before() {
        createdBy = System.getProperty("previousResult.createdBy");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
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
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Tests");
        } else if(pfGlossary.equals("RPA")) {
            leftMenu.openPageBy("Tasks");
        }

        TasksPage tasksPage = new TasksPage(driver);
        tasksPage.filterTask(taskName);

        DisplayedTasks displayedTasks = tasksPage.getTasksTable().getTasksFirstRowData();
        Assert.assertNotNull(displayedTasks,"There is no task with name: '" + taskName + "'");
        Assert.assertEquals(displayedTasks.getName(),taskName,"The name of task is not right");
        Main.report.logPass("Task has right value for 'Name'");
        Assert.assertEquals(displayedTasks.getCreatedBy(),createdBy,"The value 'Created by' for task " + taskName + " is not like expected");
        Main.report.logPass("Task has right value for 'Created by'");
        Assert.assertEquals(displayedTasks.getTechnology(),technology,"The value 'Technology' for task " + taskName + " is not like expected");
        Main.report.logPass("Task has right value for 'Technology'");

        TaskDetailsPage taskDetailsPage = tasksPage.clickTaskNameLink(displayedTasks.getLnkName(), taskName);
        Assert.assertEquals(taskDetailsPage.getName(),taskName,"Name of task on details page is not right");
        Main.report.logPass("Task has right name");
        Assert.assertEquals(taskDetailsPage.getCreatedBy(),createdBy,"The value for 'CREATED BY' on Details page is not right");
        Main.report.logPass("Task has right value for 'CREATED BY'");
        Assert.assertEquals(taskDetailsPage.getTechnology(),technology,"The value for 'TECHNOLOGY' on Details page is not right");
        Main.report.logPass("Task has right value for 'TECHNOLOGY'");
        Assert.assertEquals(taskDetailsPage.getAssignedFolderInRepository(),scriptDirectory,"The value for 'ASSIGNED FOLDER IN REPOSITORY' on Details page is not right");
        Main.report.logPass("Task has right value for 'ASSIGNED FOLDER IN REPOSITORY'");
        Assert.assertTrue(taskDetailsPage.checkListOfCommitsIsDisplayed(),"The list of commits wasn't loaded");

    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskName", taskName);
        obj.put("taskname",taskname + " || Check details of task");
        System.setProperty("output", obj.toString());
    }

}
