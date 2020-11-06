package ai.makeitright.tests.settings.edittoken;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import org.json.JSONObject;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settings.RepositoryPage;
import ai.makeitright.utilities.DriverConfig;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class EditTokenTest extends DriverConfig {

    //from configuration:
    private String pfOrganizationCardName;
    private String pfOrganizationNameUrl;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String repositoryAddress;
    private String projectName;
    private String newGitLabAccessToken;

    //for reporting:
    private String taskname;

    @BeforeTest
    public void before() {
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfOrganizationNameUrl = System.getProperty("inputParameters.pfOrganizationNameUrl");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        repositoryAddress = System.getProperty("previousResult.result.repositoryaddress");
        taskname = System.getProperty("previousResult.taskname");
        projectName = System.getProperty("previousResult.projectName");
        newGitLabAccessToken = System.getProperty("secretParameters.newGitLabAccessToken");
    }

    @Test
    public void editRepositoryToken() {

        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();

        leftMenu.openPageBy("Repositories");

        RepositoryPage repositoryPage = new RepositoryPage(driver, pfSignInUrl, pfOrganizationNameUrl);
        Assert.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));

        repositoryPage
                .clickEditTokenButton(repositoryAddress)
                .setAccessTokenInput(newGitLabAccessToken);

        AlertStatusPopupWindow statusPopupWindow = repositoryPage.clickSaveButtonWhenEditingToken();
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"));
        Assert.assertTrue(statusPopupWindow.isAlertStatus("High five!!"));
        Assert.assertTrue(statusPopupWindow.isAlertMessage("Your token has been updated! You can keep on rockin'\uD83D\uDE46\u200D"));
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        JSONObject objResult = new JSONObject();
        obj.put("taskname", taskname + " || Edit GitLab access token");
        obj.put("projectName", projectName);
        objResult.put("repositoryaddress", repositoryAddress);
        obj.put("result", objResult);
        System.setProperty("output", obj.toString());
    }

}

