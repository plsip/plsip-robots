package ai.makeitright.tests.settings.deleterepo;

import ai.makeitright.pages.gitlab.*;
import org.json.JSONObject;
import ai.makeitright.utilities.DriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class DeleteGitLabRepositoryTest extends DriverConfig {

    //from configuration:
    private String gitLabAccessToken;
    private String gitLabSignInUrl;
    private String gitLabUsername;
    private String gitLabUserPassword;
    private String projectName;

    //for reporting:
    private String taskname;

    @Before
    public void before() {
        gitLabAccessToken = System.getProperty("secretParameters.gitLabAccessToken");
        gitLabSignInUrl = System.getProperty("inputParameters.gitLabSignInUrl");
        gitLabUsername = System.getProperty("inputParameters.gitLabUsername");
        gitLabUserPassword = System.getProperty("secretParameters.gitLabUserPassword");
        projectName = System.getProperty("previousResult.projectName");
        taskname = System.getProperty("previousResult.taskname");
    }

    @Test
    public void deleteRepository() {

        driver.get(gitLabSignInUrl);

        LoginGitLabPage loginGitLabPage = new LoginGitLabPage(driver, gitLabSignInUrl);
        loginGitLabPage
                .setUsernameField(gitLabUsername)
                .setPasswordField(gitLabUserPassword);
        ProjectsPage projectsPage = loginGitLabPage.clickSignInButton();

        ProjectDetailsPage projectDetailsPage = projectsPage.chooseProjectToDelete(projectName);

        GeneralSettingsPage generalSettingsPage = projectDetailsPage.chooseGeneralInSettings();
        generalSettingsPage
                .clickExpandInAdvancedSection()
                .clickDeleteProjectButton()
                .confirmDeleteRepo();

        AlertStatusGitPopupWindow alertStatusGitPopupWindow = new AlertStatusGitPopupWindow(driver);
        Assertions.assertTrue(alertStatusGitPopupWindow.isAlertStatus(projectName));

        projectsPage.clickUserPanel();
        projectsPage.clickOptionSignOut();
    }

    @After
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskname + " || Delete project repository on the GitLab platform");
        obj.put("projectName", projectName);
        System.setProperty("output", obj.toString());
        driver.close();
    }

}
