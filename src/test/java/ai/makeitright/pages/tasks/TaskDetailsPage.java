package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class TaskDetailsPage extends BasePage {

    @FindBy(xpath = "//span[text()='ASSIGNED FOLDER IN REPOSITORY']//following-sibling::div//button")
    private WebElement btnCopy;

    @FindBy(xpath = "//div[@id='processes-commits-panel']//div[@class='Polaris-Stack__Item']/div[@class='commits-list']")
    private WebElement lstCommits;

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

    public String getAssignedFolderInRepository() throws IOException, InterruptedException, UnsupportedFlavorException {
        click(btnCopy, "button 'Copy' to copy 'ASSIGNED FOLDER IN REPOSITORY' to clipboard");
        String assignedFolderInRepository;
        try {
            assignedFolderInRepository = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (IllegalStateException e) {
            TimeUnit.SECONDS.sleep(2);
            assignedFolderInRepository = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        }
        return assignedFolderInRepository;
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
