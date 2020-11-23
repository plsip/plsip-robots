package ai.makeitright.tests.globalarguments.singlecreateglobalarguments;

import ai.makeitright.pages.argumentscollections.ArgumentsPage;
import ai.makeitright.pages.argumentscollections.DisplayedGlobalArguments;
import ai.makeitright.pages.argumentscollections.GlobalArgumentsPage;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateGlobalArguments49Test extends DriverConfig {

    //from configuration:
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    //for reporting:

    private String collectionName;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        collectionName = System.getProperty("inputParameters.beginningCollectionName");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        String pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        Main.taskname = pfGlossary + ": TC - Global arguments - Create Global arguments collection [P20Ct-49]";
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

        Assert.assertFalse(globalArgumentsPage.isNotVisibleModalWindow(),"Modal window for adding new global arguments collection shouldn't be visible");

        collectionName = createGlobalArgumentModalWindow.getCollectionName();

        DisplayedGlobalArguments displayedGlobalArguments =
                globalArgumentsPage.getGlobalArgumentsTable()
                        .getGlobalArgumentsRowData(collectionName);

        Assert.assertNotNull(displayedGlobalArguments, "There is no global arguments collection '" + collectionName + "' on the list");
        Assert.assertTrue(globalArgumentsPage.checkAuthor(displayedGlobalArguments.getAuthor()),"Author of global arguments collection is not right");
    }

    @AfterTest
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("globalArgumentsCollection", collectionName);
        System.setProperty("output", obj.toString());
    }

}
