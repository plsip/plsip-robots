package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectDetailsPage extends BasePage {

    public ProjectDetailsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        if(!waitForVisibilityOf(projectName)) {
            Main.report.logFail("Project name is not visible");
        }
        Assertions.assertTrue(waitForVisibilityOf(projectName), "Project name is not visible");
        return true;
    }

    @FindBy(xpath = "//*[@data-testid='settings-icon']")
    private WebElement settingsIcon;

    @FindBy(xpath = "//*[@class='breadcrumb-item-text js-breadcrumb-item-text']")
    private WebElement projectName;

    @FindBy(xpath = "//a[@title='General']")
    private WebElement btnGeneral;

    public GeneralSettingsPage chooseGeneralInSettings() {
        click(settingsIcon, "'Settings' icon on the right panel");
        click(btnGeneral, "'General' button in Settings section");
        return new GeneralSettingsPage(driver);
    }
}