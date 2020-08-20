package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

    @FindBy(xpath = "//*[@class='btn btn-success']")
    private WebElement btnNewProject;

    @FindBy(xpath = "//*[@class='page-title']")
    private WebElement txtProject;

    public NewProjectPage clickNewProjectButton() {
        click(btnNewProject, "button 'New Project'");
        return new NewProjectPage(driver);
    }
}
