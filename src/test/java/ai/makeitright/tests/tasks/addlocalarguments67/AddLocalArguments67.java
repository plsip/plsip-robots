package ai.makeitright.tests.tasks.addlocalarguments67;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.tasks.TasksPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddLocalArguments67 extends DriverConfig {

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
        Main.taskname = pfGlossary + ": TC - Tasks/Tests - Details of tasks/tests - add local arguments [P20Ct-67]";
    }

    @Test
    public void displayGlobalArgumentsList() {
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();

        if (pfGlossary.equals("TA")) {
            leftMenu.openPageBy("Tests");
        } else {
            leftMenu.openPageBy("Tasks");
        }
        TasksPage tasksPage = new TasksPage(driver, pfGlossary);
//        tasksPage.filterOneTask();
    }
}
