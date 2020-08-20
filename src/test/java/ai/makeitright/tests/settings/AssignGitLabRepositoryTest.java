package ai.makeitright.tests.settings;

import ai.makeitright.pages.common.AlertStatusPopupWindow;
import org.json.JSONObject;
import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.gitlab.LoginGitLabPage;
import ai.makeitright.pages.gitlab.NewProjectPage;
import ai.makeitright.pages.gitlab.ProjectsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settigns.RepositoryPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class AssignGitLabRepositoryTest extends DriverConfig {

    //from configuration:
    private String accesstoken;
    private String companyname;
    private String email;
    private String password;
    private String passwordgitlab;
    private String powerFarmUrl;
    private String projectname;
    private String repositorytocopy;
    private String urlgitlab;
    private String usernamegitlab;

    //for reporting:
    private String repositoryAddress;

    @Before
    public void before() {
        accesstoken = System.getProperty("inputParameters.accesstoken");
        companyname = System.getProperty("inputParameters.companyname");
        email = System.getProperty("inputParameters.email");
        password = System.getProperty("inputParameters.password");
        passwordgitlab = System.getProperty("inputParameters.passwordgitlab");
        powerFarmUrl = System.getProperty("inputParameters.powerFarm_url");
        projectname = System.getProperty("inputParameters.projectname");
        repositorytocopy = System.getProperty("inputParameters.repositorytocopy");
        urlgitlab = System.getProperty("inputParameters.urlgitlab");
        usernamegitlab = System.getProperty("inputParameters.usernamegitlab");
    }

    @Test
    public void assignRepository() {

        driver.get(urlgitlab);
        driver.manage().window().maximize();

        LoginGitLabPage loginGitLabPage = new LoginGitLabPage(driver, urlgitlab);
        loginGitLabPage
                .setUsernameField(usernamegitlab)
                .setPasswordField(passwordgitlab);
        ProjectsPage projectsPage = loginGitLabPage.clickSignInButton();

        NewProjectPage newProjectPage = projectsPage.clickNewProjectButton();

        newProjectPage.chooseOption("CI/CD for external repo");
        newProjectPage.clickButtonRepoByURL();
        newProjectPage.setGitRepositoryURL(repositorytocopy);
        String projectName = newProjectPage.setProjectName(projectname);
        repositoryAddress = "https://gitlab.com/" + usernamegitlab + "/" + projectName;
        Main.report.logPass("Created project '" + projectName + "'");
        newProjectPage.clickPublicCheckbox();
        newProjectPage.clickCreateProjectButton();

        newProjectPage.clickUserPanel();
        newProjectPage.clickOptionSignOut();

        driver.get(powerFarmUrl);
        LoginPage loginPage = new LoginPage(driver, powerFarmUrl);
        loginPage
                .setEmailInput(email)
                .setPasswordInput(password);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Repositories");

        RepositoryPage repositoryPage = new RepositoryPage(driver, powerFarmUrl, companyname);
        repositoryPage
                .clickAssignRepositoryButton()
                .clickAssignGitLabRepositoryButton()
                .setAccessTokenInput(accesstoken)
                .clickSaveButton()
                .selectYourMainScriptRepository(projectName)
                .clickSaveButton();
        AlertStatusPopupWindow statusPopupWindow = new AlertStatusPopupWindow(driver);
        Assertions.assertTrue(statusPopupWindow.isBannerRibbon("GreenDark"));
        Assertions.assertTrue(statusPopupWindow.isAlertStatus("High five!!"));
        Assertions.assertTrue(statusPopupWindow.isAlertMessage("The repository has been added successfully! Let the adventure begin"));
        Assertions.assertTrue(repositoryPage.checkIfRepositoryAddressIsDisplayed(repositoryAddress));
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("repositoryaddress", repositoryAddress);
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
