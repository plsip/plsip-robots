package ai.makeitright.pages.argumentscollections;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Action;
import ai.makeitright.utilities.Main;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ArgumentsPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Add Argument']")
    private WebElement btnAddArgument;

    @FindBy(xpath = "//span[text()='Global Arguments']")
    private WebElement lnkGlobalArguments;

    @FindAll(
        @FindBy(xpath="//li[@class='Polaris-ResourceList__ItemWrapper']")
    )
    private List<WebElement> lstArguments;

    @FindAll(
            {@FindBy(xpath = "//li[@class='Polaris-ResourceList__ItemWrapper']//span[@class='dotted']")}
    )
    private List<WebElement> lstArgumentsName;

    @FindAll(
            {@FindBy(xpath = "//form//input")}
    )
    private List<WebElement> lstArgumentsValue;

    @FindBy(xpath = "//span[text()='CREATED BY']/following-sibling::p")
    private WebElement txtCreatedBy_Value;

    @FindBy(xpath = "//span[@class='Polaris-TopBar-UserMenu__Details']/p[1]")
    private WebElement txtTopPanelCreatedBy_Value;

    public ArgumentsPage(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return btnAddArgument.isDisplayed();
    }

    public boolean checkCreatedBy() {
        return txtCreatedBy_Value.getText() == txtTopPanelCreatedBy_Value.getText();
    }

    public AddArgumentModalWindow clickButtonAddArgument() {
        click(btnAddArgument,"button Add Argument");
        return new AddArgumentModalWindow(driver);
    }

    Action action = new Action(driver);

    public boolean checkIfArgumentWithValueIsDisplayed(String argumentName, String argumentValue) throws InterruptedException {
        Main.report.logInfo("Check if on the list is visible argument with name '" + argumentName + "' and value '" + argumentValue + "'");
        WebElement x = action.getItemFromTable(lstArgumentsName, argumentName);
        if (x == null) {
            Main.report.logFail("Name of argument is not like expected: '" + argumentName + "'");
            return false;
        }
        WebElement element = action.getItemFromTableAsAttributeValue(lstArgumentsValue, argumentValue);
        if (element == null) {
            Main.report.logFail("Value of argument is not like expected: '" + argumentValue + "'");
            return false;
        }
        Main.report.logPass("Name and value of argument is right");
        return true;
    }

    public boolean checkIfOneArgumentIsDisplayed() {
        return lstArguments.size() == 1;
    }

    public ArgumentsPage clickGlobalArgumentsLnk() {
        click(lnkGlobalArguments,"link 'Global Arguments'");
        return this;
    }

    public class AddArgumentModalWindow extends BasePage {

        @FindBy(xpath = "//div[@role='dialog']//h2")
        private WebElement h2;

        @FindBy(xpath = "//input[@name='key']")
        private WebElement inpArgumentName;

        @FindBy(xpath = "//input[@name='defaultValue' and not(@disabled)]")
        private WebElement inpDefaultValue;

        @FindBy(xpath = "//input[@name='isSecret']")
        private WebElement chboxEncrypted;

        @FindBy(xpath = "//button//span[text()='Save']")
        private WebElement btnSave;

        private String argumentName;
        private String defaultValue;

        public AddArgumentModalWindow(final WebDriver driver) {
            super(driver);
        }

        @Override
        protected boolean isAt() {
            waitForVisibilityOf(h2);
            return h2.getText().equals("Add argument");
        }

        public ArgumentsPage clickSaveButton() {
            click(btnSave, "button 'Save'");
            return new ArgumentsPage(driver);
        }

        public String getArgumentName() {
            return argumentName;
        }

        public AddArgumentModalWindow setDefaultValue(String defaultValue) {
            sendText(inpDefaultValue, defaultValue, "input element 'Default Value'");
            return this;
        }

        public AddArgumentModalWindow setName(String argumentName) {
            this.argumentName = argumentName + Methods.getDateTime("yyyyMMddHHmmss");
            sendText(inpArgumentName, this.argumentName, "input element 'Argument Name'");
            return this;
        }


    }

}
