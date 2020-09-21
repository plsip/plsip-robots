package ai.makeitright.tests.globalarguments.addargument;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AddArgumentTest extends DriverConfig {

    //from configuration:
    private String globalArgumentsCollection;
    private String pfCompanyName;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;

    //for reporting:

    private String argumentName;
    private String defaultValue;
    private String taskname;

    @Before
    public void before() {
        argumentName = System.getProperty("inputParameters.argumentName");
        globalArgumentsCollection = System.getProperty("previousResult.globalArgumentsCollection");
        pfCompanyName = System.getProperty("inputParameters.pfCompanyName");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        taskname = System.getProperty("previousResult.taskname");
    }

    @Test
    public void addArgument() throws InterruptedException {
        Faker faker = new Faker();
        defaultValue = faker.funnyName().name();

        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfCompanyName);
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

        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assertions.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"),"Banner ribbon on popup window is not dark green");
        Assertions.assertTrue(statusPopupWindow.isAlertStatus("Good Job!"),"There is no right status on popup window");
        Assertions.assertTrue(statusPopupWindow.isAlertMessage("Your argument has been added to the Collection. Keep on"),"There is no right alert message");

        Assertions.assertTrue(argumentsPage.checkIfOneArgumentIsDisplayed(),"There is no only one argument displayed");
        Assertions.assertTrue(
                argumentsPage.checkIfArgumentWithValueIsDisplayed(addArgumentModalWindow.getArgumentName(), defaultValue),
                "There is no visible argument with name '" + argumentName + "' and value '" + defaultValue + "'");


    }

    @After
    public void prepareJson() {
        final JSONObject obj = new JSONObject();
        obj.put("argumentName", argumentName);
        obj.put("argumentValue", defaultValue);
        obj.put("taskname",taskname + " || Add argument");
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
