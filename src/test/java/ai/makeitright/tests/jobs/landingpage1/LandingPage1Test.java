package ai.makeitright.tests.jobs.landingpage1;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.jobs.JobsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LandingPage1Test extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Jobs - Landing page [P20Ct-1]";
    }

    @Test
    public void landingPage() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Jobs");

        JobsPage jobsPage = new JobsPage(driver);
        Main.report.logPass("On the page is header text 'Jobs'");

        Assert.assertTrue(jobsPage.isJobRowDisplayed(), "There is no visible any row with job");
        Main.report.logPass("On the page is table with at least one job row");

        Main.report.logInfo("We will check headers on the jobs' table");
        Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(1,"ID"),"First column header should be 'ID'");
        Main.report.logPass("1st column header is right 'ID'");

        if (pfGlossary.equals("TA")) {
            Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(2, "Test Plan name"), "2nd column header should be 'Test Plan name'");
            Main.report.logPass("2nd column header is right 'Test Plan name'");
        } else {
            Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(2, "Workflow name"), "2nd column header should be 'Workflow name'");
            Main.report.logPass("2nd column header is right 'Workflow name'");
        }

        Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(3,"Date created"),"3rd column header should be 'Date created'");
        Main.report.logPass("3rd column header is right 'Date created'");
        Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(4,"Start date"),"4th column header should be 'Start date'");
        Main.report.logPass("4th column header is right 'Start date'");
        Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(5,"End date"),"5th column header should be 'End date'");
        Main.report.logPass("5th column header is right 'End date'");
        Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(6,"Status"),"6th column header should be 'Status'");
        Main.report.logPass("6th column header is right 'Status'");
        Assert.assertTrue(jobsPage.checkHeaderWithNumberHasValue(7,"Created by"),"7th column header should be 'Created by'");
        Main.report.logPass("7th column header is right 'Created by'");

        Main.report.logInfo("Check if jobs from first pagination page contain values in all columns");
        Assert.assertTrue(jobsPage.checkJobsFromFirstPaginationPageContainValuesInColumns(),"First row job contains value in column 1");
        Main.report.logPass("All rows on the page contain values in every columns");

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
}
