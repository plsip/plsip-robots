package ai.makeitright.tests.argumentscollections;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GlobalArgumentsTest extends DriverConfig {

    //from configuration:
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    //for reporting:
    private String repositoryAddress;

    @Before
    public void before() {
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("inputParameters.pfUserPassword");
    }

    @Test
    public void createArgumentsCollection() {

        Faker faker = new Faker();
        String nameOfArgumentsCollection = faker.funnyName().name();
        String nameOfArgument = faker.funnyName().name();
        String devaultArgumentValue = faker.funnyName().name();

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
        final GlobalArgumentsPage.CreateArgumentsCollectionModalWindow createArgumentsCollectionModalWindow =
                globalArgumentsPage.clickCreateArgumentsCollectionButton();
        ArgumentsCollectionPage argumentsCollectionPage = createArgumentsCollectionModalWindow
                .writeIntoCollectionNameInput(nameOfArgumentsCollection)
                .clickSaveButton();
        final ArgumentsCollectionPage.AddArgumentModalWindow addArgumentModalWindow =
                argumentsCollectionPage.clickAddArgumentButton();
        argumentsCollectionPage = addArgumentModalWindow
                .writeIntoArgumentNameInput(nameOfArgument)
                .writeIntoDefaultValueinput(devaultArgumentValue)
                .clickSaveButton();

//        Assertions


    }

    @After
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("repositoryaddress", repositoryAddress);
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
