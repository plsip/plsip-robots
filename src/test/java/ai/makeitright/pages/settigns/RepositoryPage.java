package ai.makeitright.pages.settigns;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class RepositoryPage extends BasePage {
    public RepositoryPage(WebDriver driver, String url) {
        super(driver,url);
    }

    @Override
    protected boolean isAt() {
        Main.report.logInfo("Current URL address: "+driver.getCurrentUrl());
        url = url.substring(0,url.lastIndexOf("/signin")) + "/mir-mvp/settings/repository";
        if(!url.equals(driver.getCurrentUrl())) {
            Main.report.logFail("Current URL is not like expected " + url);
        }
        Assert.assertEquals("Current URL address '" + driver.getCurrentUrl() + "' is not like expected '" + url + "'",url,driver.getCurrentUrl());
        return true;
    }

    @FindBy(xpath = "//button[@class='Polaris-Button']//span[text()='Assign Gitlab Repository']")
    private WebElement btnAssignGitLabRepository;

    @FindBy(xpath = "//button[@class='Polaris-Button Polaris-Button--primary']")
    private WebElement btnAssingRepository;

    @FindBy(xpath = "//button[@class='Polaris-Button Polaris-Button--primary']//span[text()='Save']")
    private WebElement btnSave;

    @FindBy(xpath = "//select[@id='PolarisSelect1']")
    private WebElement dropdownSelectYourMainScriptRepository;

    @FindAll(
            @FindBy(xpath = "//select[@id='PolarisSelect1']/option")
    )
    private List<WebElement> dropdownSelectYourMainScriptRepositoryOptions;

    @FindBy(xpath = "//input[@name='token']")
    private WebElement inpAccessToken;

    public void clickButtonAssignRepository() {
        click(btnAssingRepository, "button 'Assing Repository'");
    }

    public void clickButtonAssignGitLabRepository() {
        waitForVisibilityOf(btnAssignGitLabRepository);
        click(btnAssignGitLabRepository, "button 'Assing GitLab Repository'");
    }

    public void setAccessToken(String accesstoken) {
        sendSecretlyText(inpAccessToken, accesstoken, "input element 'Access Token'");
    }

    public void clickButtonSave() {
        click(btnSave, "button 'Save'");
    }

    public TableRepositoryAddresses getTableRepositoryAddresses() {
        return new TableRepositoryAddresses(driver);
    }

    public void selectYourMainScriptRepository(String projectName) {
        waitForBlueCircleDisappear();
        click(getItemFromDropdown(dropdownSelectYourMainScriptRepository, dropdownSelectYourMainScriptRepositoryOptions, projectName),"option '" + projectName + "' of dropdown 'Select your main scripts repository'");
    }

    public boolean existRepositoryAddress(String repositoryAddress) {
        Main.report.logInfo("Check if repository with address " + repositoryAddress + "is on the list");
        AllRepositoryAddresses allRepositoryAddresses = getTableRepositoryAddresses().getAllRepositoriesAddressesRowData(repositoryAddress);
        Assert.assertNotNull(allRepositoryAddresses);
        return false;
    }
}
