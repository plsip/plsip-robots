package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.junit.jupiter.api.Assertions;

import java.util.NoSuchElementException;

public class GlobalArgumentsPage extends BasePage {

    @FindBy(xpath="//h1")
    private WebElement h1;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inpCollectionName;

    @FindBy(xpath = "//button//span[text()='Create Global arguments']")
    private WebElement createGlobalArgumentsButton;

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

    public CreateGlobalArgumentModalWindow clickCreateGlobalArgumentsButton() {
        click(createGlobalArgumentsButton,"button 'Create Global arguments'");
        return new CreateGlobalArgumentModalWindow(driver);
    }

    public ArgumentsPage clickGlobalArgumentsCollectionNameButton(WebElement btnArgumentsCollectionName, String globalArgumentsCollectionName) {
        Assertions.assertNotNull(btnArgumentsCollectionName, "Webelement for button with name of arguments collection name '" + globalArgumentsCollectionName + "' has value null");
        click(btnArgumentsCollectionName, "button with collection named '" + globalArgumentsCollectionName + "'");
        return new ArgumentsPage(driver);
    }

    public GlobalArgumentsTable getGlobalArgumentsTable() {
        return new GlobalArgumentsTable(driver);
    }

    public boolean isNotVisibleModalWindow() {
        try {
            inpCollectionName.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

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

        public String getCollectionName() {
            return collectionName;
        }

        public CreateGlobalArgumentModalWindow setCollectionName(String collectionName) {
            this.collectionName = collectionName + Methods.getDateTime("yyyyMMddHHmmss");
            sendText(inpCollectionName, this.collectionName,"input element 'Collection name'");
            return this;
        }

        public ArgumentsPage clickSaveButton() {
            click(btnSave,"button 'Save'");
            return new ArgumentsPage(driver);
        }

    }


}
