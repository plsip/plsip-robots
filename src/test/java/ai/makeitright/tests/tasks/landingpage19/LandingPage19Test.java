package ai.makeitright.tests.tasks.landingpage19;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.TasksPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LandingPage19Test extends DriverConfig {

    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

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
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        Main.taskname = pfGlossary + ": TC - Tasks - Landing page [P20Ct-19]";
    }

    @Test
    public void landingPage() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        TasksPage tasksPage;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Tests");
            tasksPage = new TasksPage(driver, pfGlossary);
            Main.report.logPass("On the page is visible button 'Create new test'");

            Main.report.logInfo("Check if on the page is header 'Tests'");
            Assert.assertTrue(tasksPage.checkHeaderIs("Tests"),"Header text is not like expected");
        } else {
            leftMenu.openPageBy("Tasks");
            tasksPage = new TasksPage(driver, pfGlossary);
            Main.report.logPass("On the page is visible button 'Create new task'");

            Main.report.logInfo("Check if on the page is header 'Tasks'");
            Assert.assertTrue(tasksPage.checkHeaderIs("Tasks"),"Header text is not like expected");
        }

        Main.report.logInfo("Check if on the page is table with at least one row");
        Assert.assertTrue(tasksPage.isTaskRowDisplayed(), "There is no visible any row with task/test");
        Main.report.logPass("On the page is table with at least one row");

        Main.report.logInfo("Check all column headings in the table are right");
        if (pfGlossary.equals("TA")) {
            Assert.assertTrue(tasksPage.checkForColumnNumberHeaderHasValue(1,"Test"),"First column header should be 'Test'");
            Main.report.logPass("1st column header is right 'Test'");
        } else {
            Assert.assertTrue(tasksPage.checkForColumnNumberHeaderHasValue(1,"Task"),"First column header should be 'Task'");
            Main.report.logPass("1st column header is right 'Task'");
        }

        Assert.assertTrue(tasksPage.checkForColumnNumberHeaderHasValue(2, "Created by"), "2nd column header should be 'Created by'");
        Main.report.logPass("2nd column header is right 'Created by'");

        Assert.assertTrue(tasksPage.checkForColumnNumberHeaderHasValue(3,"Date created"),"3rd column header should be 'Date created'");
        Main.report.logPass("3rd column header is right 'Date created'");

        Assert.assertTrue(tasksPage.checkForColumnNumberHeaderHasValue(4,"Technology"),"4th column header should be 'Technology'");
        Main.report.logPass("4th column header is right 'Technology'");

        Assert.assertTrue(tasksPage.checkForColumnNumberHeaderHasValue(5,"Last commit"),"5th column header should be 'Last commit'");
        Main.report.logPass("5th column header is right 'Last commit'");

        Main.report.logInfo("Check if tasks from first pagination page contain values in first four columns");
        Assert.assertTrue(tasksPage.checkTasksFromFirstPaginationPageContainValuesInFirstFourColumns(),"First four columns on first page pagination task don't contain values");
        Main.report.logPass("All rows on the page contain values in first four columns");

        Main.report.logInfo("Check left menu content");
        leftMenu = new LeftMenu(driver);
        String[] leftMenuOptionsArray = {"Dashboard","Jobs","Schedule","Tests","Test Plans","Global arguments","Files","Repositories","Users","Access Token","Application Settings","Personal Settings"};
        if (pfGlossary.equals("RPA")) {
            leftMenuOptionsArray[3] = "Tasks";
            leftMenuOptionsArray[4] = "Workflows";
        }
        Assert.assertTrue(leftMenu.checkListOfOption(leftMenuOptionsArray),"On the left menu are not all expected options");
        Main.report.logPass("Left menu options are right");
    }

    @AfterTest
    public void after() {
        TopPanel topPanel = new TopPanel(driver);
        topPanel.clickTopPanelButton()
                .clickLogOutLink();
    }
}
