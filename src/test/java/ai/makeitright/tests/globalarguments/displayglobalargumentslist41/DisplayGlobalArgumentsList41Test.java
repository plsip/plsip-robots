package ai.makeitright.tests.globalarguments.displayglobalargumentslist41;

import ai.makeitright.pages.argumentscollections.GlobalArgumentsPage;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DisplayGlobalArgumentsList41Test extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Global arguments - Display Global arguments list [P20Ct-41]";
    }

    @Test
    public void displayGlobalArgumentsList() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();

        leftMenu.openPageBy("Global arguments");
        GlobalArgumentsPage globalArgumentsPage = new GlobalArgumentsPage(driver, pfSignInUrl);
        Main.report.logPass("On the page is header text 'Global arguments'");

        Main.report.logInfo("Check if on the page is button 'Create Global arguments'");
        Assert.assertTrue(globalArgumentsPage.isVisibleButtonCreateGlobalArguments(),"Button 'Create Global arguments' isn't visible");
        Main.report.logPass("Button 'Create Global arguments' is visible");

        Main.report.logInfo("Check if on the page is table with at least one row");
        Assert.assertTrue(globalArgumentsPage.isArgumentsCollectionRowDisplayed(), "There is no visible any row with arguments collection");
        Main.report.logPass("On the page is table with at least one row");

        Main.report.logInfo("Check if number of arguments collections is right as header says");
        Assert.assertEquals(globalArgumentsPage.getArgumentsCollectionNumber(),globalArgumentsPage.getArgumentsCollectionHeaderNumber());
        Main.report.logPass("Number of arguments collections equals number of arguments collections in table's header ");

        Main.report.logInfo("Check if arguments collection table in all columns have values");
        Assert.assertTrue(globalArgumentsPage.checkGlobalArgumentsTableContainValuesInAllColumns(),"In all columns of table should be values");
        Main.report.logPass("All rows on the page contain values in all columns");

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
