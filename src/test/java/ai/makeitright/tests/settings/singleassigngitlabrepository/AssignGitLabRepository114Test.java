package ai.makeitright.tests.settings.singleassigngitlabrepository;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.gitlab.*;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settings.RepositoryPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AssignGitLabRepository114Test extends DriverConfig {

    private String gitLabAccessToken;
    private String gitLabUsername;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfOrganizationNameUrl;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String projectName;
    private String repositoryAddress;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        gitLabAccessToken = System.getProperty("secretParameters.gitLabAccessToken");
        gitLabUsername = System.getProperty("inputParameters.gitLabUsername");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfOrganizationNameUrl = System.getProperty("inputParameters.pfOrganizationNameUrl");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.taskname = pfGlossary + ": TC - Repositories - Assign GitLab repository [P20Ct-114]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        projectName = System.getProperty("inputParameters.projectName");
    }

    @Test
    public void assignGitLabRepository() {
        repositoryAddress = "https://gitlab.com/" + gitLabUsername + "/" + projectName + "/";
        Main.report.logPass("******************************\nBefore test: if repository which we want to assing already exist on the platform, detach it\n");
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();

        leftMenu.openPageBy("Repositories");
        RepositoryPage repositoryPage = new RepositoryPage(driver, pfSignInUrl, pfOrganizationNameUrl);
        if(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress)) {
            repositoryPage.clickDetachRepositoryButton(repositoryAddress);
            repositoryPage.confirmDetachButton();
            Assert.assertFalse(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress),"Repository "+repositoryAddress+" is still visible after detach");
        }

        Main.report.logPass("******************************\nRepository "+repositoryAddress+" is not attach now");
        Main.report.logInfo("******************************Start test");
        driver.get(pfSignInUrl);
        leftMenu.openPageBy("Repositories");

        repositoryPage = new RepositoryPage(driver, pfSignInUrl, pfOrganizationNameUrl);
        repositoryPage
                .clickAssignRepositoryButton()
                .clickAssignGitLabRepositoryButton()
                .setAccessTokenInput(gitLabAccessToken)
                .clickSaveButton()
                .selectYourMainScriptRepository(projectName)
                .clickSaveButton();
        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"));
        Assert.assertTrue(statusPopupWindow.isAlertStatus("High five!!"));
        Assert.assertTrue(statusPopupWindow.isAlertMessage2("The repository has been added successfully! Let the adventure begin \uD83D\uDE46\u200D"));
        Assert.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));

        Main.report.logPass("******************************\n******************************\nTest ended with success\n******************************\nNow repository will be detach on the PF platform");


        driver.get(pfSignInUrl);

        leftMenu.openPageBy("Repositories");

        repositoryPage = new RepositoryPage(driver, pfSignInUrl, pfOrganizationNameUrl);
        Assert.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));

        repositoryPage.clickDetachRepositoryButton(repositoryAddress);
        repositoryPage.confirmDetachButton();


    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("repositoryaddress", repositoryAddress);
        System.setProperty("output", obj.toString());
    }
}
