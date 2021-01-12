package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.sql.Time;

public class TaskDetailsPage extends BasePage {

    @FindBy(xpath = "//span[text()='ASSIGNED FOLDER IN REPOSITORY']//following-sibling::div//button")
    private WebElement btnCopy;

    @FindBy(xpath = "//main//button/span/span[text()='Delete']")
    private WebElement btnDelete;

    @FindBy(xpath = "//div[@id='processes-commits-panel']//div[@class='Polaris-Stack__Item']/div[@class='commits-list']")
    private WebElement lstCommits;

    @FindBy(xpath = "//input[@class='Polaris-TextField__Input']")
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

    public DeleteTaskModalWindow clickDeleteButton(String param) throws InterruptedException {
        waitForClickable(btnDelete);
        click(btnDelete, "button 'Delete'");
        return new DeleteTaskModalWindow(driver, param);
    }

    public boolean checkListOfCommitsIsDisplayed() {
        Main.report.logInfo("Check if list of commits is displayed");
        try {
            waitLongForVisibilityOf(lstCommits);
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
