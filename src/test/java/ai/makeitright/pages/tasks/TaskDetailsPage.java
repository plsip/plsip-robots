package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TaskDetailsPage extends BasePage {

    @FindBy(xpath = "//div[@id='processes-commits-panel']//div[@class='Polaris-Stack__Item']/div[@class='commits-list']")
    private WebElement lstCommits;

    @FindBy(xpath = "//h2[text()='Information']")
    private WebElement txtInformation;

    @Override
    protected boolean isAt() {
        return txtInformation.isDisplayed();
    }

    public TaskDetailsPage(final WebDriver driver) {
        super(driver);
    }

    public boolean checkListOfCommitsIsDisplayed() {
        waitForBlueCircleDisappear();
        Main.report.logInfo("Check if list of commits is displayed");
        try {
            lstCommits.isDisplayed();
            Main.report.logPass("List of commits is displayed");
            return true;
        } catch(Exception e) {
            return false;
        }
    }

}
