package ai.makeitright.tests.settings;

import ai.makeitright.pages.common.LeftMenu;
import ai.makeitright.pages.gitlab.LoginGitLabPage;
import ai.makeitright.pages.gitlab.NewProjectPage;
import ai.makeitright.pages.gitlab.ProjectsPage;
import ai.makeitright.pages.login.LoginPage;
import ai.makeitright.pages.settigns.RepositoryPage;
import ai.makeitright.utilities.DriverConfig;
import ai.makeitright.utilities.Main;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AssignGitLabRepository extends DriverConfig {
    private String ACCESSTOKEN;
    private String EMAIL;
    private String PASSWORD;
    private String PASSWORDGITLAB;
    private String PROJECTNAME;
    private String REPOSITORYTOCOPY;
    private String URL;
    private String URLGITLAB;
    private String USERNAMEGITLAB;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("inputParameters.accesstoken","ZGLhP-UKdkjETYmQr-iB");
        System.setProperty("inputParameters.email","katarzyna.raczkowska@makeitright.ai");
        System.setProperty("inputParameters.password","TestyAutomatyczne");
        System.setProperty("inputParameters.passwordgitlab","jWpxghw4Re/+@/A");
        System.setProperty("inputParameters.projectname","nottouchautomated");
        System.setProperty("inputParameters.repositorytocopy","https://gitlab.com/kraczkowska/mirtodoist.git");
        System.setProperty("inputParameters.url","http://development.powerfarm.ai/signin");
        System.setProperty("inputParameters.urlgitlab","https://gitlab.com/users/sign_in");
        System.setProperty("inputParameters.usernamegitlab","kraczkowska");
    }

    @Before
    public void before() {
        ACCESSTOKEN = System.getProperty("inputParameters.accesstoken");
        EMAIL = System.getProperty("inputParameters.email");
        PASSWORD = System.getProperty("inputParameters.password");
        PASSWORDGITLAB = System.getProperty("inputParameters.passwordgitlab");
        PROJECTNAME = System.getProperty("inputParameters.projectname");
        REPOSITORYTOCOPY = System.getProperty("inputParameters.repositorytocopy");
        URL = System.getProperty("inputParameters.url");
        URLGITLAB = System.getProperty("inputParameters.urlgitlab");
        USERNAMEGITLAB = System.getProperty("inputParameters.usernamegitlab");
    }

    @Test
    public void assignRepository() {
        driver.get(URLGITLAB);
        driver.manage().window().maximize();

        LoginGitLabPage loginGitLabPage = new LoginGitLabPage(driver,URLGITLAB);
        loginGitLabPage.setUsername(USERNAMEGITLAB);
        loginGitLabPage.setPassword(PASSWORDGITLAB);
        ProjectsPage projectsPage = loginGitLabPage.clickButtonSignIn();

        NewProjectPage newProjectPage = projectsPage.clickButtonNewProject();

        newProjectPage.chooseOption("CI/CD for external repo");
        newProjectPage.clickButtonRepoByURL();
        newProjectPage.setGitRepositoryURL(REPOSITORYTOCOPY);
        String projectName = newProjectPage.setProjectName(PROJECTNAME);
        String repositoryAddress = "https://gitlab.com/" + USERNAMEGITLAB + "/" + projectName;
        Main.report.logPass("Created project '" + projectName + "'");
        newProjectPage.clickCheckboxPublic();
        newProjectPage.clickButtonCreateProject();

        newProjectPage.clickUserPanel();
        newProjectPage.clickOptionSignOut();

        driver.get(URL);
        LoginPage loginPage = new LoginPage(driver,URL);
        loginPage.setEmail(EMAIL);
        loginPage.setPassword(PASSWORD);
        LeftMenu leftMenu = loginPage.clickButtonSignIn();
        leftMenu.openPageBy("Settings");

        RepositoryPage repositoryPage = new RepositoryPage(driver,URL);
        repositoryPage.clickButtonAssignRepository();
        repositoryPage.clickButtonAssignGitLabRepository();
        repositoryPage.setAccessToken(ACCESSTOKEN);
        repositoryPage.clickButtonSave();
        repositoryPage.selectYourMainScriptRepository(projectName);
        repositoryPage.clickButtonSave();
        repositoryPage.existRepositoryAddress(repositoryAddress);


    }
}
