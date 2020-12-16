package ai.makeitright.pages.tasks;

import ai.makeitright.pages.BasePage;
import ai.makeitright.utilities.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class TasksPage extends BasePage {

    @FindBy(xpath = "//button//span[text()='Create new task']")
    private WebElement btnCreateNewTaskRPA;

    @FindBy(xpath = "//button//span[text()='Create new test']")
    private WebElement btnCreateNewTaskTA;

    @FindBy(xpath = "//input[@placeholder='Filter items']")
    private WebElement inpFilterItems;

    @FindAll(
            @FindBy(xpath = "//tbody/tr")
    )
    private List<WebElement> lstTaskRow;

    @FindBy(xpath = "//div[@class='Polaris-Header-Title']/h1")
    private WebElement txtHeader;

    private WebDriverWait wait = new WebDriverWait(driver, 10);
    private WebDriverWait waitShort = new WebDriverWait(driver, 3);

    private By getHeadersName(int headerNumber) {
        return new By.ByXPath("//thead/tr/th[" + headerNumber + "]");
    }

    @Override
    protected boolean isAt() {
        if (urlOrParam.equals("TA")) {
            return btnCreateNewTaskTA.isDisplayed();
        } else {
            return btnCreateNewTaskRPA.isDisplayed();
        }
    }

    public TasksPage(final WebDriver driver) {
        super(driver);
    }

    public TasksPage(final WebDriver driver, final String param) {
        super(driver, param);
    }

    public boolean checkForColumnNumberHeaderHasValue(int headerNumber, String expectedHeaderName) {
        String headersName = driver.findElement(getHeadersName(headerNumber)).getText();
        Main.report.logInfo("Actual headers name is '" + headersName + "'");
        return headersName.equals(expectedHeaderName);
    }

    public boolean checkHeaderIs(String expectedHeader) {
          return expectedHeader.equals(txtHeader.getText());
    }

    public TasksPage checkIfNoneRowDisplayed() {
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody/tr"),0));
        Main.report.logPass("On the tasks table is no visible row");
        return this;
    }

    public TasksPage checkIfOneRowDisplayed() {
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody/tr"),1));
        Main.report.logPass("On the tasks table is visible one row");
        return this;
    }

    public boolean checkTasksFromFirstPaginationPageContainValuesInFirstFourColumns() {
        for (WebElement row : lstTaskRow) {
            Assert.assertNotEquals(row.findElement(By.xpath("./th//span")).getText(),"","In first column row doesn't contain value");
            for (int i=1;i<4;i++) {
                Assert.assertNotEquals(row.findElement(By.xpath("./td["+i+"]")).getText(),"","In " + i + " column row doesn't contain value");
            }
        }
        return true;
    }

    public CreateTaskModalWindow clickCreateNewTaskButton() {
        click(btnCreateNewTaskRPA, "button 'Create new task'");
        return new CreateTaskModalWindow(driver);
    }

    public CreateTaskModalWindow clickCreateNewTestButton() {
        click(btnCreateNewTaskTA, "button 'Create new test'");
        return new CreateTaskModalWindow(driver);
    }

    public TaskDetailsPage clickTaskNameLink(WebElement element, String taskName) {
        click(element, "link with name of task '" + taskName + "'");
        return new TaskDetailsPage(driver);
    }

    public boolean existTask(String taskName) {
        waitShort.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tr[@class='Polaris-DataTable__TableRow']"),1));
        DisplayedTasks tasks = getTasksTable().getTasksRowData(taskName);
        if (tasks == null) {
            Main.report.logFail("Task named '" + taskName + "' doesn't exist in the tasks table");
            return false;
        }
        return true;
    }

    public TasksPage filterOneTask(String taskName) {
        Main.report.logInfo("Search task named: '" + taskName + "'");
        sendText(inpFilterItems, taskName, "input element 'Filter items'");
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody/tr"),1));
        return this;
    }

    public TasksPage filterTask(String taskName) {
        Main.report.logInfo("Search task named: '" + taskName + "'");
        sendText(inpFilterItems, taskName, "input element 'Filter items'");
        return this;
    }

    public TasksTable getTasksTable() {
        return new TasksTable(driver);
    }

    public boolean isTaskRowDisplayed() {
        return lstTaskRow.size() > 0;
    }

}
