package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Action;
import ai.makeitright.utilities.Methods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class CreateTaskModalWindow extends BasePage {

    @FindBy(xpath = "//button[@class='Polaris-Button Polaris-Button--primary']//span[text()='Create task']")
    private WebElement btnCreateTask;

    @FindBy(xpath = "//select[@name='repositoryID']")
    private WebElement dropdownScriptDirectory;

    @FindAll(
            @FindBy(xpath = "//select[@name='repositoryID']/option")
    )
    private List<WebElement> dropdownScriptDirectoryOptions;

    @FindBy(xpath = "//select[@name='technology']")
    private WebElement dropdownTechnology;

    @FindAll(
            @FindBy(xpath = "//select[@name='technology']/option")
    )
    private List<WebElement> dropdownTechnologyOptions;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement inpName;

    @FindBy(xpath = "//input[@name='directory']")
    private WebElement inpScriptDirectory;

    private String taskName;

    public CreateTaskModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return inpName.isDisplayed();
    }

    public TaskDetailsPage clickCreateTaskButton() {
        Assert.assertTrue(waitForVisibilityOf(btnCreateTask), "Button 'Create task' is not visible");
        click(btnCreateTask, "button 'Create task'");
        return new TaskDetailsPage(driver);
    }

    public String getName() {
        return taskName;
    }

    public CreateTaskModalWindow setName(String taskName) {
        this.taskName = taskName + Methods.getDateTime("yyyyMMddHHmmss");
        sendText(inpName, this.taskName, "input element 'Name'");
        return this;
    }

    public CreateTaskModalWindow selectTechnology(String technology) {
        click(getItemFromDropdown(dropdownTechnology, dropdownTechnologyOptions, technology), "option '" + technology + "' of dropdown 'Technology'");
        return this;
    }

    public CreateTaskModalWindow selectScriptDirectory(String repository) {
        click(getItemFromDropdown(dropdownScriptDirectory, dropdownScriptDirectoryOptions, repository), "option '" + repository + "' of dropdown 'Script directory'");
        return this;
    }

    public CreateTaskModalWindow setScriptDirectory(String directory) {
        new Action(driver).sendText(inpScriptDirectory, directory, "input element 'Script directory'");
        return this;
    }

}
