package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class ProjectsPage extends BasePage {
    public ProjectsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        if(!waitForVisibilityOf(txtProject)) {
            Main.report.logFail("There is no visible particular element with text 'Project'");
        }
        Assert.assertTrue(waitForVisibilityOf(txtProject), "There is no visible particular element with text 'Project'");
        return true;
    }

    @FindBy(xpath = "//a[@class='gl-button btn btn-success']")
    private WebElement btnNewProject;


    @FindBy(xpath = "//*[@class='page-title']")
    private WebElement txtProject;

    @FindAll(
            @FindBy(xpath = "//span[@class = 'project-name']")
    )
    private List<WebElement> projects;

    @FindBy(xpath = "//img[@alt='Katarzyna Raczkowska']")
    private WebElement img_GitLabUser;

    @FindBy(xpath = "//a[@class='sign-out-link']")
    private WebElement optionSignOut;

    public NewProjectPage clickNewProjectButton() {
        click(btnNewProject, "button 'New Project'");
        return new NewProjectPage(driver);
    }

    public ProjectDetailsPage chooseProjectToDelete(String name) {
        try {
            for (WebElement project : projects) {
                if (project.getText().equals(name)) {
                    click(project, "project: " + name + " to see details");
                    break;
                }
            }
            return new ProjectDetailsPage(driver);
        }
        catch (NullPointerException e) {
            Main.report.logFail("There was no repository name: " + name);
            return null;
        }
    }

    public void clickUserPanel() {
        waitForClickable(img_GitLabUser);
        click(img_GitLabUser, "image for GitLab user");
    }

    public void clickOptionSignOut() {
        click(optionSignOut, "option 'Sign out'");
    }
}
