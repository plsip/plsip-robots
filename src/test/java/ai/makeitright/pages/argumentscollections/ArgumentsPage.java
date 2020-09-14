package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Action;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ArgumentsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Add Argument']")
    private WebElement btnAddArgument;

    @FindBy(xpath = "//ul[@class='Polaris-ResourceList']")
    private WebElement lstUnordered;

    @FindAll(
            {@FindBy(xpath = "//li[@class='Polaris-ResourceList__ItemWrapper']//div[@class='text-dotted']")}
    )
    private List<WebElement> itemsOfUnorderedList;


    public ArgumentsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return btnAddArgument.isDisplayed();
    }

    public AddArgumentModalWindow clickButtonAddArgument() {
        btnAddArgument.click();
        return new AddArgumentModalWindow(driver);
    }

    Action action = new Action(driver);

    public boolean checkIfArgumentIsDisplayed(String itemOfList) throws InterruptedException {
        WebElement x = action.getItemFromUnorderedList(itemsOfUnorderedList, itemOfList);
        return x != null;
    }

    public class AddArgumentModalWindow extends BasePage {

        @FindBy(xpath = "//div[@role='dialog']//h2")
        private WebElement h2;

        @FindBy(xpath = "//input[@name='key']")
        private WebElement argumentNameInput;

        @FindBy(xpath = "//input[@name='defaultValue']")
        private WebElement argumentDefaultValueInput;

        @FindBy(xpath = "//input[@name='isSecret']")
        private WebElement chboxEncrypted;

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

        public AddArgumentModalWindow setNameInput(String argumentName) {
            argumentNameInput.sendKeys(argumentName);
            return this;
        }

        public AddArgumentModalWindow writeIntoDefaultValueInput(String defaultValue) {
            argumentDefaultValueInput.sendKeys(defaultValue);
            return this;
        }

        public ArgumentsPage clickSaveButton() {
            saveButton.click();
            return new ArgumentsPage(driver);
        }

    }

}
