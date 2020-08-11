package ai.makeitright.tests.settings;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.gitlab.LoginGitLabPage;
import ai.makeitright.pages.gitlab.NewProjectPage;
import ai.makeitright.pages.gitlab.ProjectsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settigns.RepositoryPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AssignGitLabRepositoryTest extends DriverConfig {
    private String ACCESSTOKEN;
    private String EMAIL;
    private String PASSWORD;
    private String PASSWORDGITLAB;
    private String POWER_FARM_URL;
    private String PROJECTNAME;
    private String REPOSITORYTOCOPY;
    private String URLGITLAB;
    private String USERNAMEGITLAB;

    @BeforeAll
    public static void beforeClass() {
        System.setProperty("inputParameters.accesstoken", "ZGLhP-UKdkjETYmQr-iB");
        System.setProperty("inputParameters.email", "katarzyna.raczkowska@makeitright.ai");
        System.setProperty("inputParameters.password", "TestyAutomatyczne");
        System.setProperty("inputParameters.passwordgitlab", "jWpxghw4Re/+@/A");
        System.setProperty("inputParameters.power_farm_url", "https://development.powerfarm.ai/signin");
        System.setProperty("inputParameters.projectname", "nottouchautomated");
        System.setProperty("inputParameters.repositorytocopy", "https://gitlab.com/kraczkowska/mirtodoist.git");
        System.setProperty("inputParameters.urlgitlab", "https://gitlab.com/users/sign_in");
        System.setProperty("inputParameters.usernamegitlab", "kraczkowska");
    }

    @BeforeEach
    public void before() {
        ACCESSTOKEN = System.getProperty("inputParameters.accesstoken");
        EMAIL = System.getProperty("inputParameters.email");
        PASSWORD = System.getProperty("inputParameters.password");
        PASSWORDGITLAB = System.getProperty("inputParameters.passwordgitlab");
        POWER_FARM_URL = System.getProperty("inputParameters.power_farm_url");
        PROJECTNAME = System.getProperty("inputParameters.projectname");
        REPOSITORYTOCOPY = System.getProperty("inputParameters.repositorytocopy");
        URLGITLAB = System.getProperty("inputParameters.urlgitlab");
        USERNAMEGITLAB = System.getProperty("inputParameters.usernamegitlab");
    }

    @Test
    public void assignRepository() {

        String repositoryAddress;

        driver.get(URLGITLAB);
        driver.manage().window().maximize();

        LoginGitLabPage loginGitLabPage = new LoginGitLabPage(driver, URLGITLAB);
        loginGitLabPage
                .setUsernameField(USERNAMEGITLAB)
                .setPasswordField(PASSWORDGITLAB);
        ProjectsPage projectsPage = loginGitLabPage.clickSignInButton();

        NewProjectPage newProjectPage = projectsPage.clickNewProjectButton();

        newProjectPage.chooseOption("CI/CD for external repo");
        newProjectPage.clickButtonRepoByURL();
        newProjectPage.setGitRepositoryURL(REPOSITORYTOCOPY);
        String projectName = newProjectPage.setProjectName(PROJECTNAME);
        repositoryAddress = "https://gitlab.com/" + USERNAMEGITLAB + "/" + projectName;
        Main.report.logPass("Created project '" + projectName + "'");
        newProjectPage.clickPublicCheckbox();
        newProjectPage.clickCreateProjectButton();

        newProjectPage.clickUserPanel();
        newProjectPage.clickOptionSignOut();

        driver.get(POWER_FARM_URL);
        LoginPage loginPage = new LoginPage(driver, POWER_FARM_URL);
        loginPage
                .setEmailInput(EMAIL)
                .setPasswordInput(PASSWORD);
        LeftMenu leftMenu = loginPage.clickSignInButton();
        leftMenu.openPageBy("Settings");

        RepositoryPage repositoryPage = new RepositoryPage(driver, POWER_FARM_URL);
        repositoryPage
                .clickAssignRepositoryButton()
                .clickAssignGitLabRepositoryButton()
                .setAccessTokenInput(ACCESSTOKEN)
                .clickSaveButton()
                .selectYourMainScriptRepository(projectName)
                .clickSaveButton();
        Assertions.assertTrue(repositoryPage.CheckIfRepositoryAddressIsDisplayed(repositoryAddress));

    }
}
