package ai.makeitright.tests.settings.singledetachgitlabrepository;

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

public class DetachGitLabRepository37Test extends DriverConfig {

    //from configuration:
    private String gitLabAccessToken;
    private String gitLabSignInUrl;
    private String gitLabUsername;
    private String gitLabUserPassword;
    private String pfGlossary;
    private String pfOrganizationCardName;
    private String pfOrganizationNameUrl;
    private String pfSignInUrl;
    private String pfUserEmail;
    private String pfUserPassword;
    private String projectName;
    private String repositoryToCopy;

    //for reporting:
    private String allProjectName;

    @BeforeTest
    public void before() {
        Main.channel = System.getProperty("inputParameters.channel");
        gitLabAccessToken = System.getProperty("secretParameters.gitLabAccessToken");
        gitLabSignInUrl = System.getProperty("inputParameters.gitLabSignInUrl");
        gitLabUsername = System.getProperty("inputParameters.gitLabUsername");
        gitLabUserPassword = System.getProperty("secretParameters.gitLabUserPassword");
        Main.hookUrl = System.getProperty("secretParameters.hookUrl");
        pfGlossary = System.getProperty("inputParameters.pfGlossary");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfOrganizationNameUrl = System.getProperty("inputParameters.pfOrganizationNameUrl");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        Main.pfSignInUrl = this.pfSignInUrl;
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        Main.taskname = pfGlossary + ": TC - Repositories - Detach GitLab repository that is not connected with any task or workflow [P20Ct-37]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        projectName = System.getProperty("inputParameters.projectName");
        repositoryToCopy = System.getProperty("inputParameters.repositoryToCopy");
    }

    @Test
    public void detachGitLabRepository() {
        Main.report.logPass("******************************\nBefore test create repository on GitLab:\n");
        driver.get(gitLabSignInUrl);

        LoginGitLabPage loginGitLabPage = new LoginGitLabPage(driver, gitLabSignInUrl);
        loginGitLabPage
                .setUsernameField(gitLabUsername)
                .setPasswordField(gitLabUserPassword);
        ProjectsPage projectsPage = loginGitLabPage.clickSignInButton();

        NewProjectPage newProjectPage = projectsPage.clickNewProjectButton();

        newProjectPage.chooseOption("CI/CD for external repo");
        newProjectPage.clickButtonRepoByURL();
        newProjectPage.setGitRepositoryURL(repositoryToCopy);
        allProjectName = newProjectPage.setProjectName(projectName);
        String repositoryAddress = "https://gitlab.com/" + gitLabUsername + "/" + allProjectName + "/";
        Main.report.logPass("Created project '" + allProjectName + "'");
        newProjectPage.clickPublicCheckbox();
        newProjectPage.clickCreateProjectButton();

        newProjectPage.clickUserPanel();
        newProjectPage.clickOptionSignOut();

        Main.report.logPass("******************************\nBefore test attach repository on PF platform:\n");
        driver.get(pfSignInUrl);
        LoginPage loginPage = new LoginPage(driver, pfSignInUrl, pfOrganizationCardName);
        loginPage
                .setEmailInput(pfUserEmail)
                .setPasswordInput(pfUserPassword);

        LeftMenu leftMenu = loginPage.clickSignInButton();

        leftMenu.openPageBy("Repositories");

        RepositoryPage repositoryPage = new RepositoryPage(driver, pfSignInUrl, pfOrganizationNameUrl);
        repositoryPage
                .clickAssignRepositoryButton()
                .clickAssignGitLabRepositoryButton()
                .setAccessTokenInput(gitLabAccessToken)
                .clickSaveButton()
                .selectYourMainScriptRepository(allProjectName)
                .clickSaveButton();
        Assert.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));

        Main.report.logInfo("******************************\nRepository was created on GitLab, assigned on the PF platform\nStart test\n******************************");
        driver.get(pfSignInUrl);

        leftMenu.openPageBy("Repositories");

        repositoryPage = new RepositoryPage(driver, pfSignInUrl, pfOrganizationNameUrl);
        Assert.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));

        repositoryPage.clickDetachButton(repositoryAddress);
        repositoryPage.confirmDetachButton();

        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"));
        Assert.assertTrue(statusPopupWindow.isAlertStatus("You did it! \uD83D\uDE4C"));
        Assert.assertTrue(statusPopupWindow.isAlertMessage(repositoryAddress));

        Main.report.logPass("******************************\n******************************\nTest ended with success\n******************************\nNow repository will be deleted GitLab platform");
        driver.get(gitLabSignInUrl);

        loginGitLabPage = new LoginGitLabPage(driver, gitLabSignInUrl);
        loginGitLabPage
                .setUsernameField(gitLabUsername)
                .setPasswordField(gitLabUserPassword);
        projectsPage = loginGitLabPage.clickSignInButton();
        projectsPage.filterProjects(allProjectName);
        ProjectDetailsPage projectDetailsPage = projectsPage.chooseProjectToDelete(allProjectName);

        GeneralSettingsPage generalSettingsPage = projectDetailsPage.chooseGeneralInSettings();
        generalSettingsPage
                .clickExpandInAdvancedSection()
                .clickDeleteProjectButton()
                .confirmDeleteRepo();

        AlertStatusGitPopupWindow alertStatusGitPopupWindow = new AlertStatusGitPopupWindow(driver);
        Assert.assertTrue(alertStatusGitPopupWindow.isAlertStatus("Project 'Katarzyna Raczkowska / " + allProjectName + "' is in the process of being deleted."));

        projectsPage.clickUserPanel();
        projectsPage.clickOptionSignOut();
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("projectName", allProjectName);
        System.setProperty("output", obj.toString());
    }
}
