package ai.makeitright.pages.todoist;

import ai.makeitright.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class TableAllInbox extends BasePage {
    public TableAllInbox(final WebDriver driver) {
        super(driver);
    }

    @Override
    protected boolean isAt() {
        return table.isDisplayed();
    }

    @FindBy(xpath = "//ul[@class='tasks__list']")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//ul[@class='tasks__list']/li")
    )
    private List<WebElement> tableRowsList;

    public AllInbox getAllInboxRowData(final String taskName) {
        AllInbox allInbox;
        if (tableRowsList.isEmpty()) {
            return null;
        } else {
            for (WebElement row : tableRowsList) {
                WebElement rowChboxTask = row.findElement(By.xpath(".//div/span"));
                WebElement rowTaskName = row.findElement(By.xpath("./span"));
                if (rowTaskName.getText().equals(taskName)) {
                    allInbox = new AllInbox()
                            .setChboxTask(rowChboxTask)
                            .setTaskName(rowTaskName.getText());
                    return allInbox;
                }
            }
        }
        return null;
    }
}
