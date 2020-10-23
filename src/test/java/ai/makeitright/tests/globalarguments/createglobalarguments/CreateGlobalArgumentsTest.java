package ai.makeitright.tests.globalarguments.createglobalarguments;

import ai.makeitright.pages.argumentscollections.ArgumentsPage;
import ai.makeitright.pages.argumentscollections.DisplayedGlobalArguments;
import ai.makeitright.pages.argumentscollections.GlobalArgumentsPage;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CreateGlobalArgumentsTest extends DriverConfig {

    //from configuration:
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    //for reporting:

    private String collectionName;

    @Before
    public void before() {
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        collectionName = System.getProperty("inputParameters.collectionName");
    }

    @Test
    public void createAGlobalArguments() {

        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
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
                .setCollectionName(collectionName)
                .clickSaveButton();

        argumentsPage.checkCreatedBy();
        argumentsPage.clickGlobalArgumentsLnk();

        Assertions.assertFalse(globalArgumentsPage.isNotVisibleModalWindow(),"Modal window for adding new gobal arguments collection shouldn't be visible");

        collectionName = createGlobalArgumentModalWindow.getCollectionName();

        DisplayedGlobalArguments displayedGlobalArguments =
                globalArgumentsPage.getGlobalArgumentsTable()
                        .getGlobalArgumentsRowData(collectionName);

        Assertions.assertNotNull(displayedGlobalArguments, "There is not global arguments collection '" + collectionName + "' on the list");
        Assertions.assertTrue(globalArgumentsPage.checkAuthor(displayedGlobalArguments.getAuthor()),"Author of global arguments collection is not right");
    }

    @After
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("globalArgumentsCollection", collectionName);
        obj.put("taskname", "Create Global arguments collection");
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
