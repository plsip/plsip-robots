package ai.makeitright.tests.users.displayuserdetails15;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.common.TopPanel;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.users.DisplayedUsers;
import ai.makeitright.pages.users.UserDetailsPage;
import ai.makeitright.pages.users.UsersPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DisplayUserDetails15Test extends DriverConfig {

    private String email;
    private String firstLastName;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String role;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        email = System.getProperty("inputParameters.email");
        firstLastName = System.getProperty("inputParameters.firstlastname");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        String pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        Main.taskname = pfGlossary + ": TC - Users - Display user details [P20Ct-15]";
        role = System.getProperty("inputParameters.role");
    }

    @Test
    public void displayUserDetails() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Users");

        UsersPage usersPage = new UsersPage(driver);
        usersPage.filterUsers(email);

        Main.report.logInfo("Check if only one row was searched");
        Assert.assertTrue(usersPage.checkIfOneUserIsDisplayed(),"There's not one row visible");
        Main.report.logPass("One row is displayed in the Users table");

        DisplayedUsers displayedUsers = usersPage.getUsersTable().getUsersFirstRowData();
        Assert.assertNotNull(displayedUsers);

        Main.report.logInfo("Check value of email is: '" + email + "'");
        Assert.assertEquals(displayedUsers.getEmail(),email);
        Main.report.logPass("Value is correct");

        Main.report.logInfo("Check value of first and last name is: '" + firstLastName + "'");
        Assert.assertEquals(displayedUsers.getFirstLastName(),firstLastName.replace("+"," "));
        Main.report.logPass("Value is correct");

        Main.report.logInfo("Check value of role is: '" + role + "'");
        Assert.assertEquals(displayedUsers.getRole(),role);
        Main.report.logPass("Value is correct");

        Main.report.logInfo("Check if avatar has right name");
        String srcAvatarImageFirstLastName = StringUtils.substringBetween(displayedUsers.getSrcAvatarImage(),"api/?name=","&background");
        Assert.assertEquals(srcAvatarImageFirstLastName,firstLastName);
        Main.report.logPass("Value is correct");

        Main.report.logInfo("Check color, shape of avatar depending on roles");
        if (role.equals("Admin")) {
            Assert.assertTrue(displayedUsers.getSrcAvatarImage().contains("background=f49342&rounded=true&color=fff"));
        } else {
            Assert.assertTrue(displayedUsers.getSrcAvatarImage().contains("background=f006fbb&rounded=true&color=fff"));
        }
        Main.report.logPass("Color, shape is correct");

        UserDetailsPage userDetailsPage = usersPage.clickUserRow(displayedUsers.getLnkUserDetails());
        Main.report.logInfo("Check user name on header");
        Assert.assertEquals(userDetailsPage.getUserNameHeader(),firstLastName.replace("+"," "),"User name is not right: '" + userDetailsPage.getUserNameHeader() + "'");
        Main.report.logPass("User name on header is right: '" + userDetailsPage.getUserNameHeader() + "'");

        Main.report.logInfo("Check E-mail Address");
        Assert.assertEquals(userDetailsPage.getEmailAddress(),email,"User name is not right: '" + userDetailsPage.getEmailAddress() + "'");
        Main.report.logPass("E-mail Address is right: '" + userDetailsPage.getEmailAddress() + "'");

        Main.report.logInfo("Check Role");
        Assert.assertEquals(userDetailsPage.getRole().toLowerCase(),role.toLowerCase(),"Role is not right: '" + userDetailsPage.getRole() + "'");
        Main.report.logPass("Role is right: '" + userDetailsPage.getRole() + "'");

        Main.report.logInfo("Check first and last name");
        Assert.assertEquals(userDetailsPage.getFirstLastName(),firstLastName.replace("+"," "),"First, last name is not right: '" +userDetailsPage.getFirstLastName() + "'");
        Main.report.logPass("First, last name are right: '" + userDetailsPage.getFirstLastName() + "'");

        Main.report.logInfo("Check there is date for 'CREATED'");
        Assert.assertNotEquals(userDetailsPage.getCreatedDate(),"","Created date is empty");
        Main.report.logPass("Created date: '" + userDetailsPage.getCreatedDate() + "'");
    }

//    @AfterTest
//    public void after() {
//        TopPanel topPanel = new TopPanel(driver);
//        topPanel.clickTopPanelButton()
//                .clickLogOutLink();
//    }

}
