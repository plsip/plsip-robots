package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class DeleteTaskModalWindow extends BasePage {

    @FindBy(xpath = "//div[@class='Polaris-Modal-Dialog']//button/span/span[text()='Delete']")
    private WebElement btnDelete;

    @FindBy(xpath = "//span[text()='delete the task ']/span[@style='font-weight: bold; max-width: 20ch; display: inline;']")
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
        waitForBlueCircleDisappear();
        Assert.assertTrue(waitForVisibilityOf(btnDelete));
        return btnDelete.isDisplayed();
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
            click(btnDelete, "button 'Delete'");
        } else {
            click(btnDelete, "button 'Delete'");
        }
        return new TasksPage(driver, pfGlossary);
    }
}
