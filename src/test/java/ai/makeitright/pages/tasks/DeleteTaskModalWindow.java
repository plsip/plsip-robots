package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteTaskModalWindow extends BasePage {

    @FindBy(xpath = "//div[@class='Polaris-Modal-Dialog']//button/span/span[text()='Delete Task']")
    private WebElement btnDeleteTask;

    @FindBy(xpath = "//span[text()='delete the task']/b/span/span")
    private WebElement txtTaskName;

    public DeleteTaskModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return btnDeleteTask.isDisplayed();
    }


    public boolean checkTaskNameToDelete(String taskName) {
        Main.report.logInfo("Check if on the modal window task name equals '" + taskName + "'");
        return txtTaskName.getText().equals(taskName);
    }

    public TasksPage clickDeleteTask() {
        click(btnDeleteTask, "button 'Delete Task'");
        return new TasksPage(driver);
    }
}
