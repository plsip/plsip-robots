package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateGlobalArgumentModalWindow extends BasePage {

    @FindBy(xpath="//h2")
    private WebElement h2;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inpCollectionName;

    @FindBy(xpath = "//button//span[text()='Close']")
    private WebElement btnClose;

    @FindBy(xpath = "//button//span[text()='Save']")
    private WebElement btnSave;

    private String collectionName;

    public CreateGlobalArgumentModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        waitForVisibilityOf(h2);
        return h2.getText().equals("Create Global argument");
    }

    public ArgumentsPage clickSaveButton() {
        click(btnSave,"button 'Save'");
        return new ArgumentsPage(driver);
    }

    public String getCollectionName() {
        return collectionName;
    }

    public CreateGlobalArgumentModalWindow setCollectionName(String collectionName) {
        this.collectionName = collectionName + Methods.getDateTime("yyyyMMddHHmmss");
        sendText(inpCollectionName, this.collectionName,"input element 'Collection name'");
        return this;
    }
}
