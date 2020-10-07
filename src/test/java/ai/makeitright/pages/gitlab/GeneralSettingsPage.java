package ai.makeitright.pages.gitlab;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;

public class GeneralSettingsPage extends BasePage {

    public GeneralSettingsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        if(!waitForVisibilityOf(subTitle) && !subTitle.findElement(By.xpath(".//a")).getText().equals("General Settings")) {
            Main.report.logFail("Subtitle of the page isn't 'General Settings'");
        }
        Assertions.assertTrue(waitForVisibilityOf(subTitle), "Subtitle of the page isn't 'General Settings'");
        return true;
    }

    @FindBy(xpath = "//h2[@class='breadcrumbs-sub-title']")
    private WebElement subTitle;

    @FindBy(xpath = "//section[@class='advanced-settings no-animate qa-advanced-settings settings']/div/button")
    private WebElement btnExpandAdvancedSection;

    @FindBy(xpath = "//button[@class='btn btn-danger btn-md gl-button']")
    private WebElement btnDeleteProject;

    @FindBy(xpath = "//code[@class='gl-white-space-pre-wrap']")
    private WebElement confirmationTextToPaste;

    @FindBy(xpath = "//input[@name='confirm_name_input' and @class='gl-form-input form-control']")
    private WebElement inpToConfirm;

    @FindBy(xpath = "//button[@class='btn js-modal-action-primary btn-danger btn-md gl-button']")
    private WebElement confirmDeleteProject;

    public GeneralSettingsPage clickExpandInAdvancedSection() {
        waitForClickable(btnExpandAdvancedSection);
        click(btnExpandAdvancedSection, "'Expand' button in 'Advanced' section");
        return this;
    }

    public GeneralSettingsPage clickDeleteProjectButton() {
        waitForClickable(btnDeleteProject);
        click(btnDeleteProject, "'Delete project' button");
        return this;
    }

    public GeneralSettingsPage confirmDeleteRepo() {
        waitForVisibilityOf(confirmationTextToPaste);
        String confirmation = confirmationTextToPaste.getText();
        sendText(inpToConfirm, confirmation, "input for deletion confirmation");
        try {
            click(confirmDeleteProject, "'Yes, delete project' button");
            return this;
        }
        catch (ElementNotInteractableException e) {
            Main.report.logFail("Couldn't click 'Yes, delete project' button. " + e.getMessage());
        }
        return this;
    }
}
