package ai.makeitright.pages.settigns;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RepositoryPage extends BasePage {

    @FindBy(xpath = "//button[@class='Polaris-Button']//span[text()='Assign GitLab Repository']")
    private WebElement btnAssignGitLabRepository;

    @FindBy(xpath = "//button[@class='Polaris-Button Polaris-Button--primary']")
    private WebElement btnAssingRepository;

    @FindBy(xpath = "//button[@class='Polaris-Button Polaris-Button--primary']//span[text()='Save']")
    private WebElement btnSave;

    @FindBy(xpath = "//select[@name='repositoryName']")
    private WebElement dropdownSelectYourMainScriptRepository;

    @FindAll(
            @FindBy(xpath = "//select[@name='repositoryName']/option")
    )
    private List<WebElement> dropdownSelectYourMainScriptRepositoryOptions;

    @FindBy(xpath = "//input[@name='token']")
    private WebElement inpAccessToken;

    @FindBy(xpath = "//button[@class='Polaris-Button Polaris-Button--primary Polaris-Button--destructive']")
    private WebElement btnConfirmDetachRepo;

    public RepositoryPage(final WebDriver driver, final String url, final String param) {
        super(driver, url, param);
    }

    @Override
    protected boolean isAt() {
        Main.report.logInfo("Current URL address: " + driver.getCurrentUrl());
        url = url.substring(0, url.lastIndexOf("/signin")) + "/" + param + "/settings/repositories";
        if (!(driver.getCurrentUrl().contains(url))) {
            Main.report.logFail("Current URL doesn't contain  " + url);
            return false;
        }
        return true;
    }

    public RepositoryPage clickAssignRepositoryButton() {
        click(btnAssingRepository, "button 'Assing Repository'");
        return this;
    }

    public RepositoryPage clickAssignGitLabRepositoryButton() {
        waitForVisibilityOf(btnAssignGitLabRepository);
        click(btnAssignGitLabRepository, "button 'Assing GitLab Repository'");
        return this;
    }

    public RepositoryPage setAccessTokenInput(final String accesstoken) {
        sendSecretlyText(inpAccessToken, accesstoken, "input element 'Access Token'");
        return this;
    }

    public RepositoryPage clickSaveButton() {
        click(btnSave, "button 'Save'");
        return this;
    }

    public RepositoriesAddressesTable getRepositoriesAddressesTable() {
        return new RepositoriesAddressesTable(driver);
    }

    public RepositoryPage selectYourMainScriptRepository(final String projectName) {
        waitForBlueCircleDisappear();
        click(getItemFromDropdown(dropdownSelectYourMainScriptRepository, dropdownSelectYourMainScriptRepositoryOptions, projectName), "option '" + projectName + "' of dropdown 'Select your main scripts repository'");
        return this;
    }

    public boolean checkIfRepositoryAddressIsDisplayed(final String repositoryAddress) {
        Main.report.logInfo("Check if repository with address " + repositoryAddress + " is on the list");
        DisplayedCodeRepositoryAddress displayedCodeRepositoryAddress = getRepositoriesAddressesTable().getAllRepositoriesAddressesRowData(repositoryAddress);
        Assertions.assertNotNull(displayedCodeRepositoryAddress);
        return true;
    }

    public RepositoryPage clickDetachButton(final String repositoryAddress) {
        WebElement btnDetach = getRepositoriesAddressesTable().getDesirableRow(repositoryAddress).findElement(By.xpath(".//td/a[contains(@href,'detach')]"));
        click(btnDetach, "button 'Detach Repository'");
        return this;
    }

    public RepositoryPage confirmDetachButton() {
        waitForClickable(btnConfirmDetachRepo);
        click(btnConfirmDetachRepo, "confirm button 'Detach repository'");
        return this;
    }
}
