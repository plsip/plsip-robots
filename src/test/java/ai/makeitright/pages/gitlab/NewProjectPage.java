package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class NewProjectPage extends BasePage {
    public NewProjectPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        if(!waitForVisibilityOf(txtNewProject) || !waitForVisibilityOf(txtCreateNewProject)) {
            Main.report.logFail("There is no visible particular element with text 'New Project' or 'Create new project'");
        }
        Assertions.assertTrue((waitForVisibilityOf(txtNewProject) || waitForVisibilityOf(txtCreateNewProject)), "There is no visible particular element with text 'New Project' or 'Create new project'");
        return true;
    }

    @FindBy(xpath = "//form/div/div/div/input[@name='commit']")
    private WebElement btnCreateProject;

    @FindBy(xpath = "//button[@data-track-label='cicd_for_external_repo']")
    private WebElement btnRepoByURL;

    @FindBy(xpath = "//div[2]/div/div[5]/div[2]/input[@id='project_visibility_level_20' and @type='radio']")
    private WebElement chboxPublic;

    @FindBy(xpath = "//img[@alt='Katarzyna Raczkowska']")
    private WebElement img_GitLabUser;

    @FindBy(xpath = "//div/div/div/input[@id='project_import_url']")
    private WebElement inpGitRepositoryURL;

    @FindBy(xpath = "//div[2][@id='import-url-name']/div/*[@id='project_name']")
    private WebElement inpProjectName;

    @FindBy(id = "ci-cd-project-tab")
    private WebElement optionForExternalRepository;

    @FindBy(xpath = "//*[@class='blank-state-body gl-pl-4!']/h3[contains(text(),'CI')]")
    private WebElement optionForExternalRepository2;

    @FindBy(xpath = "//a[@class='sign-out-link']")
    private WebElement optionSignOut;

    @FindBy(xpath = "//*[@class='blank-state-welcome-title gl-mt-5! gl-mb-3!']")
    private WebElement txtCreateNewProject;

    @FindBy(xpath = "//*[@class='gl-mt-0']")
    private WebElement txtNewProject;

    public void chooseOption(String s) {
        if(waitForVisibilityOf(optionForExternalRepository)) {
            click(optionForExternalRepository, "option '" + s + "'");
        } else {
            click(optionForExternalRepository2, "option '" + s + "'");
        }
    }

    public void clickButtonRepoByURL() {
        waitForVisibilityOf(btnRepoByURL);
        click(btnRepoByURL, "button 'Repo by URL'");
    }

    public void setGitRepositoryURL(String repositoryToCopy) {
        waitForVisibilityOf(inpGitRepositoryURL);
        sendText(inpGitRepositoryURL,repositoryToCopy, " input element 'Git repository URL'");
    }

    public String setProjectName(String projectName) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        projectName = projectName + formatter.format(new GregorianCalendar().getTime());
        sendText(inpProjectName, projectName, "input element 'Project name'");
        return projectName;
    }

    public void clickPublicCheckbox() {
        click(chboxPublic, "checkbox 'Public'");
    }

    public void clickCreateProjectButton() {
        click(btnCreateProject, "button 'Create project'");
    }

    public void clickUserPanel() {
        click(img_GitLabUser, "image for GitLab user");
    }

    public void clickOptionSignOut() {
        click(optionSignOut, "option 'Sign out'");
    }
}
