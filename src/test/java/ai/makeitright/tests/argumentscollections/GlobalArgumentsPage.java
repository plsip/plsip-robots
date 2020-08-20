package ai.makeitright.tests.argumentscollections;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GlobalArgumentsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create Arguments Collection']")
    private WebElement createArgumentCollectionsButton;

    public GlobalArgumentsPage(final WebDriver driver, final String url) {
        super(driver, url);
    }

    @Override
    protected boolean isAt() {
        return false;
    }

    CreateArgumentsCollectionModalWindow clickCreateArgumentsCollectionButton() {
        createArgumentCollectionsButton.click();
        return new CreateArgumentsCollectionModalWindow(driver);
    }

    public class CreateArgumentsCollectionModalWindow extends BasePage {

        @FindBy(xpath = "//input[@name='name']")
        private WebElement collectionNameInput;

        @FindBy(xpath = "//button//span[text()='Close']")
        private WebElement closeButton;

        @FindBy(xpath = "//button//span[text()='Save']")
        private WebElement saveButton;

        public CreateArgumentsCollectionModalWindow(final WebDriver driver) {
            super(driver);
        }

        @Override
        protected boolean isAt() {
            return false;
        }

        CreateArgumentsCollectionModalWindow writeIntoCollectionNameInput(String collectionName) {
            collectionNameInput.sendKeys(collectionName);
            return this;
        }

        ArgumentsCollectionPage clickSaveButton() {
            saveButton.click();
            return new ArgumentsCollectionPage(driver);
        }

    }


}
