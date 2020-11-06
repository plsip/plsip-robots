package ai.makeitright.tests.users.displayuserlist;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.users.UsersPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DisplayUserListTest extends DriverConfig {

    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    @BeforeTest
    public void before() {
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
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

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname","Display user list");
        System.setProperty("output", obj.toString());
    }


}
