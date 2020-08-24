package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TasksPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create new task']")
    private WebElement btnCreateNewTask;

    @Override
    protected boolean isAt() {
        return btnCreateNewTask.isDisplayed();
    }

    public TasksPage(final WebDriver driver) {
        super(driver);
    }

    public CreateTaskModalWindow clickCreateNewTaskButton() {
        click(btnCreateNewTask, "button 'Create new task'");
        return new CreateTaskModalWindow(driver);
    }

}
