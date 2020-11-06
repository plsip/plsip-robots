package ai.makeitright.tests.settings.deleterepo;

import ai.makeitright.pages.gitlab.*;
import org.json.JSONObject;
import ai.makeitright.utilities.DriverConfig;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteGitLabRepositoryTest extends DriverConfig {

    //from configuration:
    private String gitLabAccessToken;
    private String gitLabSignInUrl;
    private String gitLabUsername;
    private String gitLabUserPassword;
    private String projectName;

    //for reporting:
    private String taskname;

    @BeforeTest
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
        Assert.assertTrue(alertStatusGitPopupWindow.isAlertStatus(projectName));

        projectsPage.clickUserPanel();
        projectsPage.clickOptionSignOut();
    }

    @AfterTest
    public void prepareJson() {
        JSONObject obj = new JSONObject();
        obj.put("taskname", taskname + " || Delete project repository on the GitLab platform");
        obj.put("projectName", projectName);
        System.setProperty("output", obj.toString());
    }

}
