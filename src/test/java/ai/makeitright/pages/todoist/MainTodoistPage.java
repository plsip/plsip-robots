package ai.makeitright.pages.todoist;

import ai.makeitright.pages.BasePage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class MainTodoistPage extends BasePage {

    @FindBy(xpath = "//button[@data-testid='add-task']")
    private WebElement btnAddTask;

    @FindBy(xpath = "//button[@data-testid='quick-add-task-action']")
    private WebElement btnPlus_Header;

    @FindBy(xpath = "//input[@data-testid='add-task-content']")
    private WebElement inp_NameOfTask;

    public MainTodoistPage(final WebDriver driver, final String url) {
        super(driver, url);
    }

    @Override
    protected boolean isAt() {
        Assertions.assertEquals(url, driver.getCurrentUrl(),
                "Current URL address '" + driver.getCurrentUrl() + "' is not like expected '" + url + "'");
        return true;
    }

    public void clickButtonPlus_Header() {
        click(btnPlus_Header, "button '+' on header");
    }

    public TableAllInbox getTableAllInbox() {
        return new TableAllInbox(driver);
    }

    public String setTaskName(String taskName) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskName = taskName + " " + formatter.format(new GregorianCalendar().getTime());
        sendText(inp_NameOfTask, taskName, "input element for name of task");
        return taskName;
    }

    public void clickButtonAddTask() {
        click(btnAddTask, "button 'Add Task'");
    }

    public boolean isTaskAtInbox(final String taskName) {
        AllInbox allInbox = getTableAllInbox().getAllInboxRowData(taskName);
        //            Main.report.logInfo("Task with name '" + taskName + "' is not visible on the Inbox list");
        return allInbox != null;
    }

    public boolean markTaskDone(final String taskName) {
        AllInbox allInbox = getTableAllInbox().getAllInboxRowData(taskName);
        if (allInbox == null) {
            return false;
        }
        allInbox.clickChboxTask();
        return true;
    }
}
