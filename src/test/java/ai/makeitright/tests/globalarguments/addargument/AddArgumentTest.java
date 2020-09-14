package ai.makeitright.tests.globalarguments.addargument;

import ai.makeitright.pages.argumentscollections.DisplayedGlobalArguments;
import ai.makeitright.pages.argumentscollections.GlobalArgumentsPage;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

public class AddArgumentTest extends DriverConfig {

    //from configuration:
    private String globalArgumentsCollection;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    //for reporting:

    private String argumentName;
    private String defaultValue;
    private String taskname;

    @Before
    public void before() {
        globalArgumentsCollection = System.getProperty("previousResult.globalArgumentsCollection");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        taskname = System.getProperty("previousResult.taskname");
    }

    @Test
    public void createAGlobalArguments() {
        Faker faker = new Faker();
        argumentName = faker.funnyName().name();
        defaultValue = faker.funnyName().name();

        driver.manage().window().maximize();
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Global arguments");

        final GlobalArgumentsPage globalArgumentsPage =
                new GlobalArgumentsPage(driver, pfSignInUrl);

//        DisplayedGlobalArguments displayedGlobalArguments =
//                globalArgumentsPage.getGlobalArgumentsTable()
//                        .getGlobalArgumentsRowData();
    }

}
