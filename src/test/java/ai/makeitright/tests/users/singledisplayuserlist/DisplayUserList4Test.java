package ai.makeitright.tests.users.singledisplayuserlist;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.users.UsersPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DisplayUserList4Test extends DriverConfig {

    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        String pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        Main.taskname = pfGlossary + ": TC - Users - Display user list [P20Ct-4]";
    }

    @Test
    public void displayUserList() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Users");

        UsersPage usersPage = new UsersPage(driver);
        Assert.assertTrue(usersPage.checkButtonInviteNewUserIsEnabled(), "Button 'Invite new user' should be visible and enable!");
        Main.report.logPass("Button 'Invite new user' is visible and enabled");

        Assert.assertTrue(usersPage.isUsersTextDisplayed(), "There is not header text 'Users' on the page");
        Main.report.logPass("On the page is header text 'Users'");

        Assert.assertTrue(usersPage.isUserRowDisplayed(), "There is no visible any row with user");
        Main.report.logPass("On the page is table with at least one user row");

    }

//    @AfterTest
//    public void after() {
//        TopPanel topPanel = new TopPanel(driver);
//        topPanel.clickTopPanelButton()
//                .clickLogOutLink();
//    }

}
