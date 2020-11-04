package ai.makeitright.tests.settings;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import org.json.JSONObject;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.gitlab.LoginGitLabPage;
import ai.makeitright.pages.gitlab.NewProjectPage;
import ai.makeitright.pages.gitlab.ProjectsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settings.RepositoryPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AssignGitLabRepositoryTest extends DriverConfig {

    //from configuration:
    private String gitLabAccessToken;
    private String gitLabSignInUrl;
    private String gitLabUsername;
    private String gitLabUserPassword;
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

    @Before
    public void before() {
        gitLabAccessToken = System.getProperty("secretParameters.gitLabAccessToken");
        gitLabSignInUrl = System.getProperty("inputParameters.gitLabSignInUrl");
        gitLabUsername = System.getProperty("inputParameters.gitLabUsername");
        gitLabUserPassword = System.getProperty("secretParameters.gitLabUserPassword");
        pfOrganizationCardName = System.getProperty("inputParameters.pfOrganizationCardName");
        pfOrganizationNameUrl = System.getProperty("inputParameters.pfOrganizationNameUrl");
        pfSignInUrl = System.getProperty("inputParameters.pfSignInUrl");
        pfUserEmail = System.getProperty("inputParameters.pfUserEmail");
        pfUserPassword = System.getProperty("secretParameters.pfUserPassword");
        projectName = System.getProperty("inputParameters.projectName");
        repositoryToCopy = System.getProperty("inputParameters.repositoryToCopy");
    }

    @Test
    public void assignRepository() {

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
        Assertions.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"));
        Assertions.assertTrue(statusPopupWindow.isAlertStatus("High five!!"));
        Assertions.assertTrue(statusPopupWindow.isAlertMessage2("The repository has been added successfully! Let the adventure begin \uD83D\uDE46\u200D"));
        Assertions.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        JSONObject objResult = new JSONObject();
        obj.put("taskname","Assign GitLab repository");
        obj.put("projectName", allProjectName);
        objResult.put("repositoryaddress", repositoryAddress);
        obj.put("result",objResult);
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
