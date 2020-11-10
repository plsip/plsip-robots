package ai.makeitright.tests.globalarguments.singleaddargument;

import ai.makeitright.pages.argumentscollections.ArgumentsPage;
import ai.makeitright.pages.argumentscollections.DisplayedGlobalArguments;
import ai.makeitright.pages.argumentscollections.GlobalArgumentsPage;
import ai.makeitright.pages.common.AlertStatusPopupWindow;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import com.github.javafaker.Faker;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddArgumentTest extends DriverConfig {

    //from configuration:
    private String channel;
    private String globalArgumentsCollection;
    private String hookUrl;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String slackFlag;

    //for reporting:
    private String argumentName;
    private String defaultValue;
    private String taskname;

    @BeforeTest
    public void before() {
        argumentName = System.getProperty("inputParameters.beginningArgumentName");
        channel = System.getProperty("inputParameters.channel");
        Main.channel = this.channel;
        globalArgumentsCollection = System.getProperty("inputParameters.globalArgumentsCollection");
        hookUrl = System.getProperty("secretParameters.hookUrl");
        Main.hookUrl = this.hookUrl;
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.taskname = pfGlossary + ": TC - Global arguments - Add argument [P20Ct-71]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
    }

    @Test
    public void addArgument() throws InterruptedException {
        Faker faker = new Faker();
        defaultValue = faker.funnyName().name();

        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Global arguments");

        final GlobalArgumentsPage globalArgumentsPage =
                new GlobalArgumentsPage(driver, pfSignInUrl);

        DisplayedGlobalArguments displayedGlobalArguments =
                globalArgumentsPage.getGlobalArgumentsTable()
                        .getGlobalArgumentsRowData(globalArgumentsCollection);

        ArgumentsPage argumentsPage = globalArgumentsPage
                .clickGlobalArgumentsCollectionNameButton(displayedGlobalArguments
                                .getBtnArgumentsCollectionName(),
                        globalArgumentsCollection);

        ArgumentsPage.AddArgumentModalWindow addArgumentModalWindow = argumentsPage.clickButtonAddArgument();

        argumentsPage = addArgumentModalWindow.setName(argumentName)
                .setDefaultValue(defaultValue)
                .clickSaveButton();
        argumentName = addArgumentModalWindow.getArgumentName();

        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"),"Banner ribbon on popup window is not dark green");
        Assert.assertTrue(statusPopupWindow.isAlertStatus("Good Job!"),"There is no right status on popup window");
        Assert.assertTrue(statusPopupWindow.isAlertMessage("Your argument has been added to the Collection. Keep on"),"There is no right alert message");

//        Assert.assertTrue(argumentsPage.checkIfOneArgumentIsDisplayed(),"There is no only one argument displayed");
        Assert.assertTrue(
                argumentsPage.checkIfArgumentWithValueIsDisplayed(argumentName, defaultValue),
                "There is no visible argument with name '" + argumentName + "' and value '" + defaultValue + "'");


    }

    @AfterTest
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("argumentName", argumentName);
        obj.put("argumentValue", defaultValue);
        System.setProperty("output", obj.toString());
    }
}
