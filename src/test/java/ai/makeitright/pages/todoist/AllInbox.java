package ai.makeitright.pages.todoist;

import org.openqa.selenium.WebElement;

public class AllInbox {

    private WebElement chboxTask;
    private String taskName;

    public void clickChboxTask() {
        chboxTask.click();
    }

    public String getTaskName() {
        return taskName;
    }

    public AllInbox setTaskName(final String taskName) {
        this.taskName = taskName;
        return this;
    }

    public AllInbox setChboxTask(final WebElement chboxTask) {
        this.chboxTask = chboxTask;
        return this;
    }
}
