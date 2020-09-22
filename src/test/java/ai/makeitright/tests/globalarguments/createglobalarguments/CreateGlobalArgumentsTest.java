package ai.makeitright.tests.globalarguments.createglobalarguments;

import ai.makeitright.pages.argumentscollections.ArgumentsPage;
import ai.makeitright.pages.argumentscollections.DisplayedGlobalArguments;
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

public class CreateGlobalArgumentsTest extends DriverConfig {

    //from configuration:
    private String pfCompanyName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    //for reporting:

    private String nameOfArgumentsCollection;

    @Before
    public void before() {
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
    }

    @Test
    public void createAGlobalArguments() {

        Faker faker = new Faker();
        nameOfArgumentsCollection = faker.funnyName().name();

        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfCompanyName);
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
                .setCollectionName(nameOfArgumentsCollection)
                .clickSaveButton();

        argumentsPage.checkCreatedBy();
        argumentsPage.clickGoBackLnk();

        nameOfArgumentsCollection = createGlobalArgumentModalWindow.getCollectionName();

        DisplayedGlobalArguments displayedGlobalArguments =
                globalArgumentsPage.getGlobalArgumentsTable()
                        .getGlobalArgumentsRowData(nameOfArgumentsCollection);

        Assertions.assertNotNull(displayedGlobalArguments, "There is not global arguments collection '" + nameOfArgumentsCollection + "' on the list");
        Assertions.assertTrue(globalArgumentsPage.checkAuthor(displayedGlobalArguments.getAuthor()),"Author of global arguments collection is not right");
    }

    @After
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("globalArgumentsCollection", nameOfArgumentsCollection);
        obj.put("taskname", "Create Global arguments collection");
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
