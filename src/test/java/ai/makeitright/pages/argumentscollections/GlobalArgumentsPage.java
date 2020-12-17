package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class GlobalArgumentsPage extends BasePage {

    @FindBy(xpath="//h1")
    private WebElement h1;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inpCollectionName;

    @FindBy(xpath = "//button//span[text()='Create Global arguments']")
    private WebElement btnCreateGlobalArguments;

    @FindAll(
            @FindBy(xpath = "//ul[@class='Polaris-ResourceList']/li")
    )
    private List<WebElement> lstArgumentsCollections;

    @FindBy(xpath = "//div[contains(@class,'HeaderTitleWrapper')]")
    private WebElement txtTableHeading;

    @FindBy(xpath = "//span[@class='Polaris-TopBar-UserMenu__Details']/p[1]")
    private WebElement txtTopPanelCreatedBy_Value;

    public GlobalArgumentsPage(final WebDriver driver, final String url) {
        super(driver, url);
    }

    @Override
    protected boolean isAt() {
        return h1.getText().equals("Global arguments");
    }

    public boolean checkAuthor(String author) {
        return txtTopPanelCreatedBy_Value.getText().equals(author);
    }

    public boolean checkGlobalArgumentsTableContainValuesInAllColumns() {
        for (WebElement row : lstArgumentsCollections) {
            Assert.assertNotEquals(row.findElement(By.xpath("./div/div/div/div/div/div[1]/span")).getText(),"","Arguments collection row doesn't have name");
            Assert.assertNotEquals((row.findElement(By.xpath("./div/div/div/div/div/div[2]")).getText()).replace("Author: ",""),"","Arguments collection '" + row.findElement(By.xpath("./div/div/div/div/div/div[1]/span")).getText() + "' row doesn't have value for 'Author'");
            Assert.assertNotEquals((row.findElement(By.xpath("./div/div/div/div/div/div[3]")).getText()).replace("Created: ",""),"","Arguments collection '" + row.findElement(By.xpath("./div/div/div/div/div/div[1]/span")).getText() + "' row doesn't have value for 'Creted'");
            Assert.assertNotNull(row.findElement(By.xpath("./div/div/div/div/div/div[4]//button/span/span[text()='Delete']")),"Arguments collection '" + row.findElement(By.xpath("./div/div/div/div/div/div[1]/span")).getText() + "' row doesn't contain button 'Delete'");
        }
        return true;
    }

    public CreateGlobalArgumentModalWindow clickCreateGlobalArgumentsButton() {
        click(btnCreateGlobalArguments,"button 'Create Global arguments'");
        return new CreateGlobalArgumentModalWindow(driver);
    }

    public ArgumentsPage clickGlobalArgumentsCollectionNameButton(WebElement btnArgumentsCollectionName, String globalArgumentsCollectionName) {
        Assert.assertNotNull(btnArgumentsCollectionName, "Webelement for button with name of arguments collection name '" + globalArgumentsCollectionName + "' has value null");
        click(btnArgumentsCollectionName, "button with collection named '" + globalArgumentsCollectionName + "'");
        return new ArgumentsPage(driver);
    }

    public int getArgumentsCollectionHeaderNumber() {
        return Integer.parseInt(txtTableHeading.getText().replaceAll("\\D+",""));
    }

    public int getArgumentsCollectionNumber() {
        return lstArgumentsCollections.size();
    }

    public GlobalArgumentsTable getGlobalArgumentsTable() {
        return new GlobalArgumentsTable(driver);
    }

    public boolean isArgumentsCollectionRowDisplayed() {
        return lstArgumentsCollections.size() > 0;
    }

    public boolean isNotVisibleModalWindow() {
        try {
            inpCollectionName.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVisibleButtonCreateGlobalArguments() {
        return btnCreateGlobalArguments.isDisplayed();
    }
}
