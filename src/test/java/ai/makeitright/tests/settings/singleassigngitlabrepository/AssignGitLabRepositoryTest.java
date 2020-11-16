package ai.makeitright.tests.settings.singleassigngitlabrepository;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.gitlab.LoginGitLabPage;
import ai.makeitright.pages.gitlab.NewProjectPage;
import ai.makeitright.pages.gitlab.ProjectsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settings.RepositoryPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AssignGitLabRepositoryTest extends DriverConfig {

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
    private String repositoryAddress;
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
        Main.taskname = pfGlossary + ": TC - Repositories - Assign GitLab repository [P20Ct-114]";
        Main.slackFlag = System.getProperty("inputParameters.slackFlag");
        projectName = System.getProperty("inputParameters.projectName");
        repositoryToCopy = System.getProperty("inputParameters.repositoryToCopy");
    }

    @Test
    public void assignGitLabRepository() {

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
        repositoryAddress = "https://gitlab.com/" + gitLabUsername + "/" + allProjectName + "/";
        Main.report.logPass("Created project '" + allProjectName + "'");
        newProjectPage.clickPublicCheckbox();
        newProjectPage.clickCreateProjectButton();

        newProjectPage.clickUserPanel();
        newProjectPage.clickOptionSignOut();

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
        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assert.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"));
        Assert.assertTrue(statusPopupWindow.isAlertStatus("High five!!"));
        Assert.assertTrue(statusPopupWindow.isAlertMessage2("The repository has been added successfully! Let the adventure begin \uD83D\uDE46\u200D"));
        Assert.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        JSONObject objResult = new JSONObject();
        obj.put("projectName", allProjectName);
        objResult.put("repositoryaddress", repositoryAddress);
        obj.put("result",objResult);
        System.setProperty("output", obj.toString());
    }
}
