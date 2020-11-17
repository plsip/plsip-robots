package ai.makeitright.pages.settings;

import ai.makeitright.pages.BasePage;
import ai.makeitright.pages.common.AlertStatusPopupWindow;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class RepositoryPage extends BasePage {

    @FindBy(xpath = "//button[@class='Polaris-Button']//span[text()='Assign GitLab Repository']")
    private WebElement btnAssignGitLabRepository;

    @FindBy(xpath = "//button[@class='Polaris-Button Polaris-Button--primary']")
    private WebElement btnAssignRepository;

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
        urlOrParam = urlOrParam.substring(0, urlOrParam.lastIndexOf("/signin")) + "/" + param + "/settings/repositories";
        if (!(driver.getCurrentUrl().contains(urlOrParam))) {
            Main.report.logFail("Current URL doesn't contain  " + urlOrParam);
            return false;
        }
        return true;
    }

    public RepositoryPage clickAssignRepositoryButton() {
        click(btnAssignRepository, "button 'Assign Repository'");
        return this;
    }

    public RepositoryPage clickAssignGitLabRepositoryButton() {
        waitForClickable(btnAssignGitLabRepository);
        click(btnAssignGitLabRepository, "button 'Assign GitLab Repository'");
        return this;
    }

    public RepositoryPage setAccessTokenInput(final String accesstoken) {
        waitForVisibilityOf(inpAccessToken);
        sendSecretlyText(inpAccessToken, accesstoken, "input element 'Access Token'");
        return this;
    }

    public RepositoryPage clickSaveButton() {
        click(btnSave, "button 'Save'");
        return this;
    }

    public AlertStatusPopupWindow clickSaveButtonWhenEditingToken() {
        click(btnSave, "button 'Save'");
        waitForBlueCircleDisappearLong();
        return new AlertStatusPopupWindow(driver);
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
        Assert.assertNotNull(displayedCodeRepositoryAddress);
        return true;
    }

    public RepositoryPage clickDetachButton(final String repositoryAddress) {
        WebElement btnDetach = getRepositoriesAddressesTable().getDesirableRow(repositoryAddress).findElement(By.xpath(".//td/a[contains(@href,'detach')]"));
        click(btnDetach, "'Detach Repository' button");
        return this;
    }

    public RepositoryPage confirmDetachButton() {
        waitForClickable(btnConfirmDetachRepo);
        click(btnConfirmDetachRepo, "confirm 'Detach repository' button");
        return this;
    }

    public RepositoryPage clickEditTokenButton(final String repositoryAddress) {
        WebElement btnEdit = getRepositoriesAddressesTable().getDesirableRow(repositoryAddress).findElement(By.xpath(".//td/a[contains(@href,'edit')]"));
        click(btnEdit, "'Edit Token' button");
        return this;
    }
}
