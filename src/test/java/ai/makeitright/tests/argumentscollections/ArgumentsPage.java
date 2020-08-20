package ai.makeitright.tests.argumentscollections;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class ArgumentsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Add Argument']")
    WebElement addArgumentButton;

    @FindAll(
            {@FindBy(xpath = "//li[@class='Polaris-ResourceList__ItemWrapper']")}
    )
    WebElement itemsOfUnorderedList;


    public ArgumentsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return addArgumentButton.isDisplayed();
    }

    AddArgumentModalWindow clickAddArgumentButton() {
        addArgumentButton.click();
        return new AddArgumentModalWindow(driver);
    }

    Action action = new Action(driver);

    public class AddArgumentModalWindow extends BasePage {

        @FindBy(xpath = "//h2")
        private WebElement h2;

        @FindBy(xpath = "//input[@name='key']")
        private WebElement argumentNameInput;

        @FindBy(xpath = "//input[@name='defaultValue']")
        private WebElement argumentDefaultValueInput;

        @FindBy(xpath = "//input[@name='isSecret']")
        private WebElement isEncryptedCheckbox;

        @FindBy(xpath = "//button//span[text()='Save']")
        private WebElement saveButton;

        public AddArgumentModalWindow(final WebDriver driver) {
            super(driver);
        }

        @Override
        protected boolean isAt() {
            waitForVisibilityOf(h2);
            return h2.getText().equals("Add argument");
        }

        AddArgumentModalWindow writeIntoArgumentNameInput(String argumentName) {
            argumentNameInput.sendKeys(argumentName);
            return this;
        }

        AddArgumentModalWindow writeIntoDefaultValueinput(String defaultValue) {
            argumentDefaultValueInput.sendKeys(defaultValue);
            return this;
        }

        ArgumentsPage clickSaveButton() {
            saveButton.click();
            return new ArgumentsPage(driver);
        }

    }

}
