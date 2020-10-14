package ai.makeitright.tests.users.displayuserlist;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.users.UsersPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DisplayUserListTest extends DriverConfig {

    private String pfCompanyName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    @Before
    public void before() {
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
    }

    @Test
    public void displayUserList() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfCompanyName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Users");

        UsersPage usersPage = new UsersPage(driver);
        Assertions.assertTrue(usersPage.checkButtonInviteNewUserIsEnabled(), "Button 'Invite new user' should be visible and enable!");
        Main.report.logPass("Button 'Invite new user' is visible and enabled");

        Assertions.assertTrue(usersPage.isUsersTextDisplayed(), "There is not header text 'Users' on the page");
        Main.report.logPass("On the page is header text 'Users'");

        Assertions.assertTrue(usersPage.isUserRowDisplayed(), "There is no visible any row with user");
        Main.report.logPass("On the page is table with at least one user row");

    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname","Display user list");
        System.setProperty("output", obj.toString());
        driver.close();
    }


}
