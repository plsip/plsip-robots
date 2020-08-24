package ai.makeitright.tests.argumentscollections;

import ai.makeitright.pages.argumentscollections.ArgumentsPage;
import ai.makeitright.pages.argumentscollections.GlobalArgumentsPage;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GlobalArgumentsTest extends DriverConfig {

    //from configuration:
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    //for reporting:
    private String nameOfArgumentsCollection;

    @Before
    public void before() {
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("inputParameters.pfUserPassword");
    }

    @Test
    public void createArgumentsCollection() throws InterruptedException {

        Faker faker = new Faker();
        nameOfArgumentsCollection = faker.funnyName().name();
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
        final GlobalArgumentsPage.CreateGlobalArgumentModalWindow createGlobalArgumentModalWindow =
                globalArgumentsPage.clickCreateGlobalArgumentsButton();
        ArgumentsPage argumentsPage = createGlobalArgumentModalWindow
                .writeIntoCollectionNameInput(nameOfArgumentsCollection)
                .clickSaveButton();
        final ArgumentsPage.AddArgumentModalWindow addArgumentModalWindow =
                argumentsPage.clickButtonAddArgument();
        argumentsPage = addArgumentModalWindow
                .setNameInput(nameOfArgument)
                .writeIntoDefaultValueInput(devaultArgumentValue)
                .clickSaveButton();

        Assertions.assertTrue(
                argumentsPage.checkIfArgumentIsDisplayed(nameOfArgument));

    }

    @After
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("globalargumentscollection", nameOfArgumentsCollection);
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
