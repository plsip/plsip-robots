package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GlobalArgumentsPage extends BasePage {

    @FindBy(xpath="//h1")
    private WebElement h1;

    @FindBy(xpath = "//button//span[text()='Create Global arguments']")
    private WebElement createGlobalArgumentsButton;

    public GlobalArgumentsPage(final WebDriver driver, final String url) {
        super(driver, url);
    }

    @Override
    protected boolean isAt() {
        return h1.getText().equals("Global arguments");
    }

    public CreateGlobalArgumentModalWindow clickCreateGlobalArgumentsButton() {
        createGlobalArgumentsButton.click();
        return new CreateGlobalArgumentModalWindow(driver);
    }

    public class CreateGlobalArgumentModalWindow extends BasePage {

        @FindBy(xpath="//h2")
        private WebElement h2;

        @FindBy(xpath = "//input[@name='name']")
        private WebElement collectionNameInput;

        @FindBy(xpath = "//button//span[text()='Close']")
        private WebElement closeButton;

        @FindBy(xpath = "//button//span[text()='Save']")
        private WebElement saveButton;

        public CreateGlobalArgumentModalWindow(final WebDriver driver) {
            super(driver);
        }

        @Override
        protected boolean isAt() {
            waitForVisibilityOf(h2);
            return h2.getText().equals("Create Global argument");
        }

        public CreateGlobalArgumentModalWindow writeIntoCollectionNameInput(String collectionName) {
            collectionNameInput.sendKeys(collectionName);
            return this;
        }

        public ArgumentsPage clickSaveButton() {
            saveButton.click();
            return new ArgumentsPage(driver);
        }

    }


}
