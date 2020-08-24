package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class CreateTaskModalWindow extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create task']")
    private WebElement btnCreateTask;

    @FindBy(className = "name")
    private WebElement inpName;

    private String taskName;

    public CreateTaskModalWindow(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return btnCreateTask.isDisplayed();
    }

    public CreateTaskModalWindow setName(String taskName) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        this.taskName = taskName + formatter.format(new GregorianCalendar().getTime());
        sendText(inpName, taskName, "input element 'Name'");
        return this;
    }
}
