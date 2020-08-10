package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectsPage extends BasePage {
    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        if(!waitForVisibilityOf(txtProject)) {
            Main.report.logFail("There is no visible particular element with text 'Project'");
        }
        Assert.assertTrue("There is no visible particular element with text 'Project'", waitForVisibilityOf(txtProject));
        return true;
    }

    @FindBy(xpath = "//*[@class='btn btn-success']")
    private WebElement btnNewProject;

    @FindBy(xpath = "//*[@class='page-title']")
    private WebElement txtProject;

    public NewProjectPage clickButtonNewProject() {
        click(btnNewProject, "button 'New Project'");
        return new NewProjectPage(driver);
    }
}
