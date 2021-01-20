package ai.makeitright.tests.workflows.landingpage60;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.workflows.WorkflowsPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LandingPage60Test extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Workflows - Landing page [P20Ct-60]";
    }

    @Test
    public void landingPage() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        WorkflowsPage workflowsPage;
        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Test Plans");
            workflowsPage = new WorkflowsPage(driver, pfGlossary);
            Main.report.logPass("On the page is visible button 'Create new test plan'");

            Main.report.logInfo("Check if on the page is header 'Test Plans'");
            Assert.assertTrue(workflowsPage.checkHeaderIs("Test Plans"), "Header text is not like expected");
        } else {
            leftMenu.openPageBy("Workflows");
            workflowsPage = new WorkflowsPage(driver, pfGlossary);
            Main.report.logPass("On the page is visible button 'Create new workflow'");

            Main.report.logInfo("Check if on the page is header 'Workflows'");
            Assert.assertTrue(workflowsPage.checkHeaderIs("Workflows"), "Header text is not like expected");
        }

        Main.report.logInfo("Check if on the page is table with at least one row");
        Assert.assertTrue(workflowsPage.isWorkflowRowDisplayed(), "There is no visible any row with workflow/test plan");
        Main.report.logPass("On the page is table with at least one row");

        Main.report.logInfo("Check all column headings in the table are right");
        if (pfGlossary.equals("TA")) {
            Assert.assertTrue(workflowsPage.checkForColumnNumberHeaderHasValue(1, "Test Plan"), "First column header should be 'Test Plan'");
            Main.report.logPass("1st column header is right 'Test Plan'");
        } else {
            Assert.assertTrue(workflowsPage.checkForColumnNumberHeaderHasValue(1, "Workflow"), "First column header should be 'Workflow'");
            Main.report.logPass("1st column header is right 'Workflow'");
        }

        Assert.assertTrue(workflowsPage.checkForColumnNumberHeaderHasValue(2, "Created by"), "2nd column header should be 'Created by'");
        Main.report.logPass("2nd column header is right 'Created by'");

        Assert.assertTrue(workflowsPage.checkForColumnNumberHeaderHasValue(3, "Date created"), "3rd column header should be 'Date created'");
        Main.report.logPass("3rd column header is right 'Date created'");

        Assert.assertTrue(workflowsPage.checkForColumnNumberHeaderHasValue(4, "Type"), "4th column header should be 'Type'");
        Main.report.logPass("4th column header is right 'Technology'");

        Main.report.logInfo("Check if workflows from first pagination page contain values in first four columns");
        Assert.assertTrue(workflowsPage.checkWorkflowsFromFirstPaginationPageContainValuesInFirstFourColumns(), "First row job contains value in column 1");
        Main.report.logPass("All rows on the page contain values in first four columns");

        Main.report.logInfo("Check if on first pagination page every workflow row contains 'Create job' button");
        Assert.assertTrue(workflowsPage.checkWorkflowsFromFirstPaginationPageContainCreateJobButton(), "First row job contains value in column 1");
        Main.report.logPass("All rows on the page contain values in first four columns");

        Main.report.logInfo("Check left menu content");
        leftMenu = new LeftMenu(driver);
        String[] leftMenuOptionsArray = {"Dashboard", "Jobs", "Schedule", "Tests", "Test Plans", "Global arguments", "Files", "Repositories", "Users", "Access Token", "Application Settings", "Personal Settings"};
        if (pfGlossary.equals("RPA")) {
            leftMenuOptionsArray[3] = "Tasks";
            leftMenuOptionsArray[4] = "Workflows";
        }
        Assert.assertTrue(leftMenu.checkListOfOption(leftMenuOptionsArray), "On the left menu are not all expected options");
        Main.report.logPass("Left menu options are right");
    }

//    @AfterTest
//    public void after() {
//        TopPanel topPanel = new TopPanel(driver);
//        topPanel.clickTopPanelButton()
//                .clickLogOutLink();
//    }
}
