package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DeleteTaskModalWindow extends BasePage {

    @FindBy(xpath = "//div[@class='Polaris-Modal-Dialog']//button/span/span[text()='Delete Task']")
    private WebElement btnDeleteTask;

    @FindBy(xpath = "//div[@class='Polaris-Modal-Dialog']//button/span/span[text()='Delete Test']")
    private WebElement btnDeleteTest;

    @FindBy(xpath = "//span[text()='delete the task']/b/span/span")
    private WebElement txtTaskName;

    @FindBy(xpath = "//span[text()='delete the test ']/span[@style='font-weight: bold; max-width: 20ch; display: inline;']")
    private WebElement txtTestName;

    public DeleteTaskModalWindow(final WebDriver driver) {
        super(driver);
    }

    public DeleteTaskModalWindow(final WebDriver driver, String param) {
        super(driver, param);
    }

    @Override
    protected boolean isAt() {
        if (urlOrParam.equals("TA")) {
            return btnDeleteTest.isDisplayed();
        } else {
            return btnDeleteTask.isDisplayed();
        }

    }


    public boolean checkTaskNameToDelete(String taskName, String pfGlossary) {
        if (pfGlossary.equals("TA")) {
            Main.report.logInfo("Check if on the modal window test name equals '" + taskName + "'");
            return txtTestName.getText().equals(taskName);
        } else {
            Main.report.logInfo("Check if on the modal window task name equals '" + taskName + "'");
            return txtTaskName.getText().equals(taskName);
        }

    }

    public TasksPage clickDeleteTask(String pfGlossary) {
        if (pfGlossary.equals("TA")) {
            click(btnDeleteTest, "button 'Delete Test'");
        } else {
            click(btnDeleteTask, "button 'Delete Task'");
        }
        return new TasksPage(driver, pfGlossary);
    }
}
