package ai.makeitright.tests.settings.detachrepo;

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

public class DetachGitLabRepositoryTest extends DriverConfig {

    //from configuration:
    private String pfOrganizationCardName;
    private String pfOrganizationNameUrl;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String repositoryAddress;
    private String projectName;

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
    }

    @Test
    public void detachRepository() {

        driver.get(pfSignInUrl);

        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();

        leftMenu.openPageBy("Repositories");

        RepositoryPage repositoryPage = new RepositoryPage(driver, pfSignInUrl, pfOrganizationNameUrl);
        Assert.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));

        repositoryPage.clickDetachButton(repositoryAddress);
        repositoryPage.confirmDetachButton();

        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"));
        Assert.assertTrue(statusPopupWindow.isAlertStatus("You did it! \uD83D\uDE4C"));
        Assert.assertTrue(statusPopupWindow.isAlertMessage(repositoryAddress));
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        JSONObject objResult = new JSONObject();
        obj.put("taskname", taskname + " || Detach GitLab repository");
        obj.put("projectName", projectName);
        objResult.put("repositoryaddress", repositoryAddress);
        obj.put("result", objResult);
        System.setProperty("output", obj.toString());
    }

}

