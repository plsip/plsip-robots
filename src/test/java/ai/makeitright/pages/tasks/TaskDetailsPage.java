package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TaskDetailsPage extends BasePage {

    @FindBy(xpath = "//span[text()='ASSIGNED FOLDER IN REPOSITORY']//following-sibling::div//button")
    private WebElement btnCopy;

    @FindBy(xpath = "//div[@id='processes-commits-panel']//div[@class='Polaris-Stack__Item']/div[@class='commits-list']")
    private WebElement lstCommits;

    @FindBy(xpath = "PolarisTextField3")
    private WebElement txtAssignedFolderInRepository;

    @FindBy(xpath = "//div[@class='Polaris-Stack Polaris-Stack--distributionFill']//span[text()='CREATED BY']//following-sibling::p")
    private WebElement txtCreatedBy;

    @FindBy(xpath = "//h2[text()='Information']")
    private WebElement txtInformation;

    @FindBy(xpath = "//div[@class='Polaris-Header-Title']")
    private WebElement txtName;

    @FindBy(xpath = "//div[@class='Polaris-TextContainer Polaris-TextContainer--spacingTight']//span[text()='TECHNOLOGY']/following-sibling::div/p")
    private WebElement txtTechnology;

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

    public String getAssignedFolderInRepository() {
        return txtAssignedFolderInRepository.getAttribute("value");
    }

    public String getCreatedBy() {
        return txtCreatedBy.getText();
    }

    public String getName() {
        return txtName.getText();
    }

    public String getTechnology() {
        return txtTechnology.getText();
    }

}
